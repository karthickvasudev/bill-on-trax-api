package com.billontrax.modules.core.permission.repositories;

import com.billontrax.modules.core.permission.dto.PermissionFlatFetchDto;
import com.billontrax.modules.core.permission.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {

	@Query("""
			select new com.billontrax.modules.core.permission.dto
						.PermissionFlatFetchDto(f.name,f.featureCode,p.name,p.permissionCode)
			from RoleUserMap rum
			         inner join Role r on rum.roleId = r.id
			         inner join PermissionGroup pg on r.permissionGroupId = pg.id
			         inner join PermissionFeaturePermissionGroupMap pfpgm on pg.id = pfpgm.permissionGroupId
			         inner join Features f on pfpgm.featureId = f.id
			         inner join Permissions p on pfpgm.permissionId = p.id
			where rum.userId = :userId
			""")
	List<PermissionFlatFetchDto> fetchPermissions(Long userId);
}
