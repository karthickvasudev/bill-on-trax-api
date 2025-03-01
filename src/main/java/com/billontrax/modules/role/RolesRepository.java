package com.billontrax.modules.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface RolesRepository extends JpaRepository<Role, BigInteger> {

}
