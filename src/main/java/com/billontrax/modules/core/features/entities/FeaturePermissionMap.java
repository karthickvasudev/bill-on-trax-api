package com.billontrax.modules.core.features.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "FeaturePermissionMap")
@Table(name = "feature_permission_map")
public class FeaturePermissionMap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String featureId;
	private String permissionId;
}
