package com.billontrax.modules.core.role.repositories;

import com.billontrax.modules.core.role.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface RolesRepository extends JpaRepository<Role, Long> {

}
