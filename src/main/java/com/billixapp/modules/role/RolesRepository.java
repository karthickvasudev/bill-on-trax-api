package com.billixapp.modules.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, BigInteger> {

}
