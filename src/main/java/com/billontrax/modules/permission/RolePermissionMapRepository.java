package com.billontrax.modules.permission;

import com.billontrax.modules.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface RolePermissionMapRepository extends JpaRepository<RolePermissionMap, BigInteger> {

    @Query("""
            select p.name from Roles r
            join RolePermissionMap rpm on r.id = rpm.roleId
            join Permissions p on p.id = rpm.permissionId
            where r.name = :roleName
            """)
    List<PermissionNames> fetchPermissionListByRole(RoleName roleName);
}
