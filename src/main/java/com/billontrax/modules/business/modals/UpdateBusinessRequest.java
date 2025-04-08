package com.billontrax.modules.business.modals;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateBusinessRequest {
	private String name;
	private String address;
	private String city;
	private String state;
	private String zipcode;
}
