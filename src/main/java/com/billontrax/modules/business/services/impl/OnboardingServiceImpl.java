package com.billontrax.modules.business.services.impl;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.business.dtos.OnboardingDetailsDto;
import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.business.services.BusinessService;
import com.billontrax.modules.business.services.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {
	private final BusinessService businessService;

	@Override
	public OnboardingDetailsDto getDetailsByInviteId(String inviteId) {
		if (inviteId == null || inviteId.isEmpty()) {
			throw new ErrorMessageException("Your invitation link is invalid. Please try again or contact support.");
		}
		Optional<OnboardingDetailsDto> optionalOnboardingDetail = businessService.getBusinessDetailsByInviteId(
				inviteId);
		if (optionalOnboardingDetail.isEmpty() || !optionalOnboardingDetail.get().getBusiness().getStatus().equals(
				BusinessStatus.INVITED)) {
			throw new ErrorMessageException("Your invitation link is invalid. Please try again or contact support.");
		}
		return optionalOnboardingDetail.get();
	}
}
