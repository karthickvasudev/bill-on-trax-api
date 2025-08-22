package com.billontrax.modules.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.business.dtos.OnboardingDetailsDto;
import com.billontrax.modules.business.dtos.UpdateBusinessDetailsDto;
import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.business.services.BusinessService;
import com.billontrax.modules.business.services.OnboardingService;
import com.billontrax.modules.core.user.dto.UpdateUserInformationRequest;
import com.billontrax.modules.core.user.services.UserService;
import com.billontrax.modules.store.dtos.CreateStoreDto;
import com.billontrax.modules.store.services.StoreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {
	private final BusinessService businessService;
	private final UserService userService;
	private final StoreService storeService;

	@Override
	public OnboardingDetailsDto getOnboardingDetailsByInviteId(String inviteId) {
		Optional<OnboardingDetailsDto> optionalOnboardingDetail = businessService.getOnboardingDetailsByInviteId(
				inviteId);
		if (optionalOnboardingDetail.isEmpty()) {
			throw new ErrorMessageException("Your invitation link is invalid. Please try again or contact support.");
		}
		return optionalOnboardingDetail.get();
	}

	@Override
	public OnboardingDetailsDto updateBusinessDetails(String inviteId,
			UpdateBusinessDetailsDto updateBusinessDetailsDto) {
		OnboardingDetailsDto onboardingDetails = getOnboardingDetailsByInviteId(inviteId);
		businessService.updateBusinessDetails(onboardingDetails.getBusiness().getId(), updateBusinessDetailsDto);
		return getOnboardingDetailsByInviteId(inviteId);
	}

	@Override
	public OnboardingDetailsDto updateOwnerDetails(String inviteId,
			UpdateUserInformationRequest updateOwnerDetailsDto) {
		OnboardingDetailsDto onboardingDetails = getOnboardingDetailsByInviteId(inviteId);
		userService.updateUserInformation(onboardingDetails.getOwner().getId(), updateOwnerDetailsDto);
		businessService.updateBusinessStatus(onboardingDetails.getBusiness().getId(),
				BusinessStatus.OWNER_DETAILS_UPDATED);
		return getOnboardingDetailsByInviteId(inviteId);
	}

	@Override
	public void createStores(String inviteId, List<CreateStoreDto> createStoreDtos) {
		OnboardingDetailsDto onboardingDetails = getOnboardingDetailsByInviteId(inviteId);
		for (CreateStoreDto createStoreDto : createStoreDtos) {
			createStoreDto.setBusinessId(onboardingDetails.getBusiness().getId());
			storeService.createStore(createStoreDto);
		}
		businessService.updateBusinessStatus(onboardingDetails.getBusiness().getId(), BusinessStatus.ACTIVE);
	}
}
