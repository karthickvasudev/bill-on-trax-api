package com.billontrax.modules.core.role.services.impl;

import com.billontrax.modules.core.role.entities.Role;
import com.billontrax.modules.core.role.entities.RoleUserMap;
import com.billontrax.modules.core.role.repositories.RoleUserMapRepository;
import com.billontrax.modules.core.role.repositories.RolesRepository;
import com.billontrax.modules.core.role.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	private final RolesRepository rolesRepository;
	private final RoleUserMapRepository roleUserMapRepository;

	@Override
	public void createRole(String roleName, Long businessId, Long permissionGroupId, Long userId) {
		Role role = new Role();
		role.setName(roleName);
		role.setBusinessId(businessId);
		role.setPermissionGroupId(permissionGroupId);
		Role savedRole = rolesRepository.save(role);
		assignRole(userId, savedRole.getId());
	}

	@Override
	public void assignRole(Long userId, Long roleId) {
		RoleUserMap roleUserMap= new RoleUserMap();
		roleUserMap.setUserId(userId);
		roleUserMap.setRoleId(roleId);
		roleUserMapRepository.save(roleUserMap);
	}
}
