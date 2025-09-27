package com.billontrax.modules.customer.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

import com.billontrax.modules.customer.enums.CustomerType;

@Data
public class CustomerDto {
    private Long id;
    private Long businessId;
    private String customerCode;
    private CustomerType type;
    private String name;
    private String email;
    private String phone;
    private String alternatePhone;
    private String billingAddress;
    private String shippingAddress;
    private String taxId;
    private BigDecimal outstandingLimit;
    private String note;
    private Boolean isDeleted;
    private Long createdBy;
    private Long updatedBy;
    private String createdTime;
    private String updatedTime;
    private List<CustomerContactDto> contacts;
}
