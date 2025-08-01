package com.billontrax.modules.business.services;

import com.billontrax.modules.business.dtos.OnboardingDetailsDto;
import com.billontrax.modules.business.dtos.UpdateBusinessDetailsDto;
import com.billontrax.modules.core.user.dto.UpdateUserInformationRequest;
import com.billontrax.modules.store.dtos.CreateStoreDto;

import java.util.List;

public interface OnboardingService {

	OnboardingDetailsDto getOnboardingDetailsByInviteId(String inviteId);

	OnboardingDetailsDto updateBusinessDetails(String inviteId, UpdateBusinessDetailsDto updateBusinessDetailsDto);

	OnboardingDetailsDto updateOwnerDetails(String inviteId, UpdateUserInformationRequest updateOwnerDetailsDto);

	void createStores(String inviteId, List<CreateStoreDto> createStoreDtos);
}
