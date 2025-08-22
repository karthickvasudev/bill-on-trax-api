package com.billontrax.modules.business.dtos;

import java.util.Date;

import com.billontrax.modules.business.enums.BusinessStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessListDto {
	private Long id;
	private String name;
	@Enumerated(EnumType.STRING)
	private BusinessStatus status;
	private String email;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String logoUrl;
	private Date createdTime;
	private Date updatedTime;

	private Long ownerId;
	private String ownerName;
	private String ownerEmail;
	private String ownerPhoneNumber;
}
