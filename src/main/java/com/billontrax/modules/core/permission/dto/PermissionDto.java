package com.billontrax.modules.core.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionDto {
	private String name;
	private String permissionCode;
}
