package com.billontrax.modules.business.services.impl;

import com.billontrax.common.config.PropertyConfig;
import com.billontrax.common.services.EmailService;
import com.billontrax.common.services.FileUploadService;
import com.billontrax.common.utils.RandomUtil;
import com.billontrax.modules.business.dtos.BusinessDetailDto;
import com.billontrax.modules.business.dtos.CreateBusinessRequest;
import com.billontrax.modules.business.entities.Business;
import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.business.repositories.BusinessRepository;
import com.billontrax.modules.business.services.BusinessService;
import com.billontrax.modules.core.role.services.RoleService;
import com.billontrax.modules.core.template.enums.TemplateName;
import com.billontrax.modules.core.template.services.TemplateService;
import com.billontrax.modules.core.user.dto.CreateUserRequest;
import com.billontrax.modules.core.user.entities.User;
import com.billontrax.modules.core.user.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

	private final BusinessRepository businessRepository;
	private final FileUploadService fileUploadService;
	private final UserService userService;
	private final RoleService roleService;
	private final EmailService emailService;
	private final TemplateService templateService;
	private final PropertyConfig propertyConfig;

	@Override
	@Transactional
	public Business createBusiness(CreateBusinessRequest body) {
		Business business = new Business();
		business.setName(body.getName());
		business.setEmail(body.getEmail());
		business.setPhoneNumber(body.getPhoneNumber());
		business.setAddress(body.getAddress());
		business.setCity(body.getCity());
		business.setState(body.getState());
		business.setCountry(body.getCountry());
		business.setZipCode(body.getZipCode());
		business.setStatus(BusinessStatus.CREATED);
		Business save = businessRepository.save(business);
		if (business.getLogoUrl() != null) {
			String url = fileUploadService.uploadFile(save.getId(), "business", body.getLogo());
			save.setLogoUrl(url);
			business = businessRepository.saveAndFlush(save);
		}
		CreateUserRequest ownerInformation = body.getOwnerInformation();
		ownerInformation.setBusinessId(business.getId());
		User user = userService.createUser(ownerInformation);
		business.setOwnerId(user.getId());
		businessRepository.saveAndFlush(business);
		roleService.createRole("Owner", business.getId(), 2L, user.getId()); //2L owner permission group
		return business;
	}

	@Override
	@Transactional
	public void sendBusinessInvite(Long businessId) {
		Business business = businessRepository.findById(businessId).orElseThrow();
		business.setStatus(BusinessStatus.INVITED);
		String inviteId = RandomUtil.getShortUniqueId();
		business.setInviteId(inviteId);
		businessRepository.save(business);
		User user = userService.getUser(business.getOwnerId());
		String inviteLink = "%s/app/onboarding/%s".formatted(propertyConfig.getAppUrl(), inviteId);
		Map<String, Object> parameters = Map.of("userName", user.getName(), "inviteLink", inviteLink);
		String template = templateService.fetchTemplate(TemplateName.BUSINESS_INVITE, true, parameters);
		emailService.sendEmail(user.getEmail(), "Welcome to Bill on Trax", template);
	}

	@Override
	public BusinessDetailDto getBusinessDetailsById(Long businessId) {
		return businessRepository.fetchBusinessDetail(businessId);
	}
}
