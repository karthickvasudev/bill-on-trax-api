package com.billontrax.modules.business.services;

import com.billontrax.modules.business.dtos.*;
import com.billontrax.modules.business.entities.Business;
import com.billontrax.modules.business.enums.BusinessStatus;

import java.util.List;
import java.util.Optional;

public interface BusinessService {
	Business createBusiness(CreateBusinessRequest body);

	void sendBusinessInvite(Long businessId);

	BusinessDetailDto getBusinessDetailsById(Long businessId);

	Optional<OnboardingDetailsDto> getOnboardingDetailsByInviteId(String inviteId);

	Business updateBusinessDetails(Long businessId, UpdateBusinessDetailsDto updateBusinessDetailsDto);

	void updateBusinessStatus(Long id, BusinessStatus businessStatus);

	List<BusinessListDto> searchBusiness();
}
