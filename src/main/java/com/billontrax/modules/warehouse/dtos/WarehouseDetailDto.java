package com.billontrax.modules.warehouse.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class WarehouseDetailDto extends WarehouseSummaryDto {
    private String address;
    private Date createdTime;
    private Long createdBy;
    private Date updatedTime;
    private Long updatedBy;
}

