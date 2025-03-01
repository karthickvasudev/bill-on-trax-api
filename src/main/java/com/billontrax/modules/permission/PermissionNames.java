package com.billontrax.modules.permission;

import lombok.Getter;

@Getter
public enum PermissionNames {
    NAVIGATE_BUSINESS("NAVIGATE_BUSINESS"), NAVIGATE_CHECK("NAVIGATE_CHECK");

    private final String value;
    PermissionNames(String value) {
        this.value = value;
    }
}
