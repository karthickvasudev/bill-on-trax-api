package com.billontrax.modules.warehouse.dtos;

import lombok.Data;

@Data
public class WarehouseUpdateRequest {
    private String name;
    private String address;
    private Boolean isActive;
}

