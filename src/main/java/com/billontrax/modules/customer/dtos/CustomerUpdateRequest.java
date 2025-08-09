package com.billontrax.modules.customer.dtos;

import lombok.Data;
import java.math.BigDecimal;

import com.billontrax.modules.customer.entities.CustomerType;

@Data
public class CustomerUpdateRequest {
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
}
