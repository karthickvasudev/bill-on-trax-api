package com.billontrax.modules.onboarding;

import com.billontrax.exceptions.ErrorMessageException;
import com.billontrax.modules.business.Business;
import com.billontrax.modules.business.BusinessRepository;
import com.billontrax.modules.business.IBusinessService;
import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.business.modals.UpdateBusinessRequest;
import com.billontrax.modules.onboarding.modals.OnboardingDetailsDto;
import com.billontrax.modules.user.IUserService;
import com.billontrax.modules.user.UserRepository;
import com.billontrax.modules.user.modals.ResetPasswordRequest;
import com.billontrax.modules.user.modals.UpdateUserInformationRequest;
import com.billontrax.modules.user.modals.UserProfileDto;
import com.billontrax.modules.verification.Verification;
import com.billontrax.modules.verification.VerificationRepository;
import com.billontrax.modules.verification.VerificationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;

@Service
@AllArgsConstructor
public class OnboardingService {
	private final VerificationRepository verificationRepository;
	private final BusinessRepository businessRepository;
	private final UserRepository userRepository;
	private final IBusinessService businessService;
	private final IUserService userService;

	public OnboardingDetailsDto fetchInviteInformation(String token) {
		Verification verification = verificationRepository.fetchByTypeAndToken(VerificationType.BUSINESS_INVITE, token)
				.orElseThrow(() -> new ErrorMessageException("Something went wrong! please contact system support."));
		Map<String, Object> parameters = verification.getParameters();
		BigInteger businessId = BigInteger.valueOf((Integer) parameters.get("businessId"));
		BigInteger ownerId = BigInteger.valueOf((Integer) parameters.get("ownerId"));
		Business business = businessRepository.findById(businessId).orElseThrow(
				() -> new ErrorMessageException("Business information not found! Please contact system support."));
		UserProfileDto userProfile = userRepository.fetchUserProfileById(ownerId)
				.orElseThrow(() -> new ErrorMessageException("User details not found! Please contact system support."));
		return new OnboardingDetailsDto(business, userProfile);
	}

	public OnboardingDetailsDto updateBusinessInformation(String token, UpdateBusinessRequest updateBusinessRequest) {
		OnboardingDetailsDto onboardingDetailsDto = this.fetchInviteInformation(token);
		BigInteger businessId = onboardingDetailsDto.getBusinessDetails().getId();
		businessService.updateBusinessInformation(businessId, updateBusinessRequest);
		businessService.updateStatus(businessId, BusinessStatus.BUSINESS_DETAILED_UPDATED);
		return fetchInviteInformation(token);
	}

	public OnboardingDetailsDto updateUserInformation(String token, UpdateUserInformationRequest updateUserInformationRequest) {
		OnboardingDetailsDto onboardingDetailsDto = this.fetchInviteInformation(token);
		BigInteger businessId = onboardingDetailsDto.getBusinessDetails().getId();
		BigInteger userId = onboardingDetailsDto.getUserProfile().getId();
		userService.updateUserInformation(userId, updateUserInformationRequest);
		businessService.updateStatus(businessId, BusinessStatus.USER_DETAILS_UPDATED);
		return fetchInviteInformation(token);
	}

	public void updatePassword(String token, ResetPasswordRequest body) {
		OnboardingDetailsDto onboardingDetailsDto = this.fetchInviteInformation(token);
		BigInteger businessId = onboardingDetailsDto.getBusinessDetails().getId();
		BigInteger userId = onboardingDetailsDto.getUserProfile().getId();
		userService.updatePassword(userId, body);
		businessService.updateStatus(businessId, BusinessStatus.ACTIVE);
	}
}
