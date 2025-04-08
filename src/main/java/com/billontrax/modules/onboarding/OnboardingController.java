package com.billontrax.modules.onboarding;

import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.models.ApiResponse;
import com.billontrax.common.models.ResponseStatus;
import com.billontrax.modules.business.modals.UpdateBusinessRequest;
import com.billontrax.modules.onboarding.modals.OnboardingDetailsDto;
import com.billontrax.modules.user.modals.ResetPasswordRequest;
import com.billontrax.modules.user.modals.UpdateUserInformationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/onboarding")
@AllArgsConstructor
public class OnboardingController {
	private final OnboardingService onboardingService;

	@GetMapping("{token}")
	public ApiResponse<OnboardingDetailsDto> fetchInviteInformation(@PathVariable String token) {
		ApiResponse<OnboardingDetailsDto> response = new ApiResponse<>();
		response.setStatus(new ResponseStatus(ResponseCode.OK));
		response.setData(onboardingService.fetchInviteInformation(token));
		return response;
	}

	@PutMapping("business-information/{token}")
	public ApiResponse<OnboardingDetailsDto> updateBusinessInformation(@PathVariable String token,
			@RequestBody UpdateBusinessRequest updateBusinessRequest) {
		ApiResponse<OnboardingDetailsDto> response = new ApiResponse<>();
		response.setStatus(new ResponseStatus(ResponseCode.OK, "Business Information Updated Successfully."));
		response.setData(onboardingService.updateBusinessInformation(token, updateBusinessRequest));
		return response;
	}

	@PutMapping("user-information/{token}")
	public ApiResponse<OnboardingDetailsDto> updateUserInformation(@PathVariable String token,
			@RequestBody UpdateUserInformationRequest updateUserInformationRequest) {
		ApiResponse<OnboardingDetailsDto> response = new ApiResponse<>();
		response.setStatus(new ResponseStatus(ResponseCode.OK, "User Information Updated Successfully."));
		response.setData(onboardingService.updateUserInformation(token, updateUserInformationRequest));
		return response;
	}

	@PutMapping("update-password/{token}")
	public ApiResponse<OnboardingDetailsDto> updatePassword(@PathVariable String token, @RequestBody
			ResetPasswordRequest body){
		ApiResponse<OnboardingDetailsDto> response = new ApiResponse<>();
		response.setStatus(new ResponseStatus(ResponseCode.OK, "Password Updated Successfully."));
		onboardingService.updatePassword(token, body);
		return response;
	}
}
