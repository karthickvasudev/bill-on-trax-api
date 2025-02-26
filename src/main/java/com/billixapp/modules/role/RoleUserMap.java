package com.billixapp.modules.role;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity(name = "RoleUserMap")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleUserMap {
    @Id
    private BigInteger id;
    private BigInteger userId;
    private BigInteger roleId;
}
