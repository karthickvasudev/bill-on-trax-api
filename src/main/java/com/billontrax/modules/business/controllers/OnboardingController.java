package com.billontrax.modules.business.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.business.dtos.OnboardingDetailsDto;
import com.billontrax.modules.business.dtos.UpdateBusinessDetailsDto;
import com.billontrax.modules.business.services.OnboardingService;
import com.billontrax.modules.core.user.dto.UpdateUserInformationRequest;
import com.billontrax.modules.store.dtos.CreateStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {
	private final OnboardingService onboardingService;

	@GetMapping("{inviteId}")
	public Response<OnboardingDetailsDto> fetchOnboardingDetailsByInviteId(@PathVariable String inviteId) {
		Response<OnboardingDetailsDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK, "Onboarding details fetched successfully"));
		response.setData(onboardingService.getOnboardingDetailsByInviteId(inviteId));
		return response;
	}

	@PutMapping("{inviteId}/update-business")
	public Response<OnboardingDetailsDto> updateBusinessDetails(@PathVariable String inviteId,
			@RequestBody UpdateBusinessDetailsDto updateBusinessDetailsDto) {
		Response<OnboardingDetailsDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK, "Business details updated successfully"));
		response.setData(onboardingService.updateBusinessDetails(inviteId, updateBusinessDetailsDto));
		return response;
	}

	@PutMapping("{inviteId}/update-owner-details")
	public Response<OnboardingDetailsDto> updateOwnerDetails(@PathVariable String inviteId,
			@RequestBody UpdateUserInformationRequest updateUserInformationDto) {
		Response<OnboardingDetailsDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK, "Owner details updated successfully"));
		response.setData(onboardingService.updateOwnerDetails(inviteId, updateUserInformationDto));
		return response;
	}

	@PostMapping("{inviteId}/create-stores")
	public Response<Void> createStores(@PathVariable String inviteId,
			@RequestBody List<CreateStoreDto> createStoreDtos) {
		Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Stores created successfully. please login to continue."));
		onboardingService.createStores(inviteId, createStoreDtos);
		return response;
	}
}
