package com.billontrax.modules.store.entities;

import com.billontrax.common.entities.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "Store")
@Table(name = "stores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long businessId;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String logoUrl;
	private Boolean isDeleted = false;
	private Long createdBy;
}


