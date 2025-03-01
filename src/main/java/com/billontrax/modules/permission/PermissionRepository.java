package com.billontrax.modules.permission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PermissionRepository extends JpaRepository<Permission, BigInteger> {
}
