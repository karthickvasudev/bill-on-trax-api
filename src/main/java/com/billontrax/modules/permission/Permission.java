package com.billontrax.modules.permission;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Permissions")
public class Permission {
    @Id
    private BigInteger id;
    @Enumerated(EnumType.STRING)
    private PermissionNames name;
}
