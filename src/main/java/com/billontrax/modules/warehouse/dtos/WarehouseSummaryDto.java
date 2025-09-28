package com.billontrax.modules.warehouse.dtos;

import lombok.Data;
import java.util.List;

@Data
public class WarehouseSummaryDto extends WarehouseDto {
    private Long productCount; // distinct products
    private Integer totalStockQuantity; // sum of stockQuantity
    private List<WarehouseProductStockDto> productStocks; // product-wise breakdown
}

