package com.billontrax.modules.store.dtos;

import com.billontrax.common.dtos.FileUploadDto;
import lombok.Data;

@Data
public class CreateStoreDto {
	private Long businessId;
	private String name;
	private String description;
	private String email;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private FileUploadDto logo;
}
