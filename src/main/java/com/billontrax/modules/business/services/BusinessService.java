package com.billontrax.modules.business.services;

import com.billontrax.modules.business.dtos.BusinessDetailDto;
import com.billontrax.modules.business.dtos.CreateBusinessRequest;
import com.billontrax.modules.business.dtos.OnboardingDetailsDto;
import com.billontrax.modules.business.entities.Business;

import java.util.Optional;

public interface BusinessService {
	Business createBusiness(CreateBusinessRequest body);

	void sendBusinessInvite(Long businessId);

	BusinessDetailDto getBusinessDetailsById(Long businessId);

	Optional<OnboardingDetailsDto> getBusinessDetailsByInviteId(String inviteId);
}
