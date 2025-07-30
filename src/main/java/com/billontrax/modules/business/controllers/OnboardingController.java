package com.billontrax.modules.business.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.business.dtos.OnboardingDetailsDto;
import com.billontrax.modules.business.services.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {
	private final OnboardingService onboardingService;

	@GetMapping("{inviteId}")
	public Response<OnboardingDetailsDto> fetchOnboardingDetailsByInviteId(@PathVariable String inviteId) {
		Response<OnboardingDetailsDto> response = new Response<>();
		response.setStatus(new ResponseStatus(ResponseCode.OK, "Onboarding details fetched successfully"));
		response.setData(onboardingService.getDetailsByInviteId(inviteId));
		return response;
	}
}
