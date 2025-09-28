package com.billontrax.modules.warehouse.dtos;

import lombok.Data;

@Data
public class WarehouseProductStockDto {
    private Long productId;
    private String productName;
    private Integer stockQuantity;
}

