package com.billontrax.modules.core.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPermissionDto {
	private String feature;
	private String featureCode;
	private List<PermissionDto> permissions;
}
