package com.billontrax.modules.warehouse.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WarehouseCreateRequest {
    @NotBlank
    private String name;
    private String address;
    // createdBy handled automatically via TimestampedWithUser (CurrentUserHolder)
    private Boolean isActive = true; // default true
}

