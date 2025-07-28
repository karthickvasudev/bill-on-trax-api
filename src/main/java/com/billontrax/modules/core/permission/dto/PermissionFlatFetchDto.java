package com.billontrax.modules.core.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionFlatFetchDto {
	private String featureName;
	private String featureCode;
	private String permissionName;
	private String permissionCode;
}
