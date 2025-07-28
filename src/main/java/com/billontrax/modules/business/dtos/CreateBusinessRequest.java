package com.billontrax.modules.business.dtos;

import com.billontrax.common.dtos.FileUploadDto;
import com.billontrax.modules.core.user.dto.CreateUserRequest;
import lombok.Data;

@Data
public class CreateBusinessRequest {
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private FileUploadDto logo;
	private CreateUserRequest ownerInformation;
}
