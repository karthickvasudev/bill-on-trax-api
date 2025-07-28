package com.billontrax.modules.core.permission.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PermissionFeaturePermissionGroupMap")
@Table(name = "permission_feature_permission_group_map")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionFeaturePermissionGroupMap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long permissionGroupId;
	private Long featureId;
	private Long permissionId;
}
