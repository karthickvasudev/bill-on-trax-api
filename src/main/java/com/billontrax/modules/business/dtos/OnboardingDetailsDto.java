package com.billontrax.modules.business.dtos;

import com.billontrax.modules.business.entities.Business;
import com.billontrax.modules.core.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OnboardingDetailsDto {
	private String inviteId;
	private Business business;
	private User owner;
}
