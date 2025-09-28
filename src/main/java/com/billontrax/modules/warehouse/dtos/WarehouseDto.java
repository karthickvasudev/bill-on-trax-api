package com.billontrax.modules.warehouse.dtos;

import lombok.Data;

@Data
public class WarehouseDto {
    private Long id;
    private String name;
    private Boolean isActive;
}

