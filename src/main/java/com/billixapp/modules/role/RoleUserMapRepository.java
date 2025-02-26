package com.billixapp.modules.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface RoleUserMapRepository extends JpaRepository<RoleUserMap, BigInteger> {
    @Query("select r from RoleUserMap rum inner join Roles r on rum.roleId = r.id " +
            "where r.isDeleted = false and rum.userId = :userId")
    Optional<Role> findRoleByUserId(BigInteger userId);
}
