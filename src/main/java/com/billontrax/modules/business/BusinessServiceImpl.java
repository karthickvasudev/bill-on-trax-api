package com.billontrax.modules.business;

import com.billontrax.config.PropertyConfig;
import com.billontrax.exceptions.ErrorMessageException;
import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.business.modals.*;
import com.billontrax.modules.email.EmailTemplateName;
import com.billontrax.modules.email.EmailTemplateService;
import com.billontrax.modules.role.RoleName;
import com.billontrax.modules.role.RoleService;
import com.billontrax.modules.user.IUserService;
import com.billontrax.modules.user.User;
import com.billontrax.modules.user.UserRepository;
import com.billontrax.modules.user.modals.CreateUserRequest;
import com.billontrax.modules.user.modals.UserProfileDto;
import com.billontrax.modules.verification.Verification;
import com.billontrax.modules.verification.VerificationService;
import com.billontrax.modules.verification.VerificationType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BusinessServiceImpl implements IBusinessService {
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final IUserService userService;
    private final EmailTemplateService emailTemplateService;
    private final VerificationService verificationService;
    private final PropertyConfig propertyConfig;
    private final RoleService roleService;

    @Override
    public BigInteger inviteBusinessAndUser(NewBusinessCreateRequest body) {
        CreateBusinessRequest businessDetails = body.getCompanyDetails();
        CreateUserRequest ownerDetails = body.getOwnerDetails();
        User user = userService.createUser(ownerDetails);
        roleService.assignRole(user.getId(), RoleName.OWNER.getId());

        Business business = new Business();
        business.setName(businessDetails.getBusinessName());
        business.setOwnerId(user.getId());
        business.setAddress(businessDetails.getAddress());
        business.setCity(businessDetails.getCity());
        business.setState(businessDetails.getState());
        business.setZipcode(businessDetails.getZipcode());
        return businessRepository.save(business).getId();
    }

    @Override
    public BusinessDetailsDto fetchBusinessDetailsById(BigInteger id) {
        return businessRepository.fetchBusinessDetailsById(id)
                .orElseThrow(() -> new ErrorMessageException("Business Details not found"));
    }

    @Override
    public List<BusinessListDto> fetchBusinessList() {
        return businessRepository.fetchBusinessList();
    }

    @Override
    @Transactional
    public void sendInvite(BigInteger id) {
        Business business = businessRepository.findById(id).orElseThrow(() -> new ErrorMessageException("Business details not found"));
        UserProfileDto userProfile = userRepository.fetchUserProfileById(business.getOwnerId())
                .orElseThrow(() -> new ErrorMessageException("Business owner details not found"));

        Map<String, Object> params = Map.of("businessId", business.getId(), "ownerId", userProfile.getId());
        Verification verification = verificationService.createVerification(VerificationType.BUSINESS_INVITE, params);

        String url = propertyConfig.getBusinessInviteEndpoint().replace("{token}", verification.getToken());
        Map<String, Object> emailParams = Map.of("userName", userProfile.getFullName(), "url", url);
        emailTemplateService.sendEmail(EmailTemplateName.BUSINESS_INVITE, emailParams, List.of(userProfile.getEmail()),
                List.of("bcc"), true);
        if (business.getStatus().equals(BusinessStatus.CREATED)) {
            business.setStatus(BusinessStatus.INVITED);
        }
        businessRepository.save(business);
    }

    @Override
    public BusinessDto updateBusinessInformation(BigInteger businessId,
            UpdateBusinessRequest updateBusinessRequest) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ErrorMessageException("Business Details not found"));
        business.setName(updateBusinessRequest.getName());
        business.setAddress(updateBusinessRequest.getAddress());
        business.setCity(updateBusinessRequest.getCity());
        business.setState(updateBusinessRequest.getState());
        business.setZipcode(updateBusinessRequest.getZipcode());
        return new BusinessDto(businessRepository.save(business));
    }

    @Override
    public Business updateStatus(BigInteger businessId, BusinessStatus businessStatus) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ErrorMessageException("Business Details not found"));
        business.setStatus(businessStatus);
        return businessRepository.save(business);
    }
}
