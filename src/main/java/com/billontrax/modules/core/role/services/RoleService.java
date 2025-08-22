package com.billontrax.modules.core.role.services;

public interface RoleService {
	void createRole(String roleName, Long businessId, Long permissionGroupId, Long userId);

	void assignRole(Long userId, Long roleId);
}
