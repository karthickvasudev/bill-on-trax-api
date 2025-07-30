package com.billontrax.modules.business.services;

import com.billontrax.modules.business.dtos.OnboardingDetailsDto;

public interface OnboardingService {

	OnboardingDetailsDto getDetailsByInviteId(String inviteId);
}
