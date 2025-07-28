package com.billontrax.modules.core.features.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "Features")
@Table(name = "features")
@AllArgsConstructor
@NoArgsConstructor
public class Features {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String featureCode;
}
