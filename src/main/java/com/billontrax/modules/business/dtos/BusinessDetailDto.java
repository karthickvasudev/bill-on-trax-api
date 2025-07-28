package com.billontrax.modules.business.dtos;

import com.billontrax.modules.business.entities.Business;
import com.billontrax.modules.core.user.entities.User;
import lombok.Data;

@Data
public class BusinessDetailDto {
	private Business business;
	private User owner;

	public BusinessDetailDto(Business business, User user) {
		this.business = business;
		this.owner = user;
	}
}
