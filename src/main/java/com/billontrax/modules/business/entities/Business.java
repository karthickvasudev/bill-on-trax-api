package com.billontrax.modules.business.entities;

import com.billontrax.common.entities.Timestamped;
import com.billontrax.modules.business.enums.BusinessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Business")
@Table(name = "business")
public class Business extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long ownerId;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String logoUrl;
	private String inviteId;
	@Enumerated(EnumType.STRING)
	private BusinessStatus status;
}

