package com.billontrax.modules.business.dtos;

import com.billontrax.common.dtos.FileUploadDto;
import lombok.Data;

@Data
public class UpdateBusinessDetailsDto {
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private Boolean isDeleteLogo;
	private FileUploadDto logo;
}
