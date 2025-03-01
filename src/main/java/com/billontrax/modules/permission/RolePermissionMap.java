package com.billontrax.modules.permission;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity(name = "RolePermissionMap")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger roleId;
    private BigInteger permissionId;
}
