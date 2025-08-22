package com.billontrax.modules.core.role.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billontrax.modules.core.role.entities.Role;

public interface RolesRepository extends JpaRepository<Role, Long> {

}
