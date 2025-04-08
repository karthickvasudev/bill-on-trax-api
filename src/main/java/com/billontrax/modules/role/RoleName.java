package com.billontrax.modules.role;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public enum RoleName {
    SUPER_ADMIN(1), OWNER(2), ADMIN(3), USER(4);

    private final BigInteger id;

    RoleName(int id) {
        this.id = BigInteger.valueOf(id);
    }
}
