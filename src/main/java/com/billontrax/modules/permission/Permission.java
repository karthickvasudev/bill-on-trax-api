package com.billontrax.modules.permission;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "permissions")
public class Permission {
    @Id
    private BigInteger id;
    private String name;
}
