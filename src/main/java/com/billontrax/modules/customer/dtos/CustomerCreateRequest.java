package com.billontrax.modules.customer.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.customer.enums.CustomerType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateRequest {
    @NotNull(message = "Customer type is required")
    private CustomerType type;

    @NotNull(message = "Name is required")
    private String name;
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;
    @Pattern(regexp = "$|^\\d{10}$", message = "Alternate Phone number must be 10 digits")
    private String alternatePhone;
    private String billingAddress;
    private String shippingAddress;
    private String taxId;
    private BigDecimal outstandingLimit = BigDecimal.ZERO;
    private String note;
    private List<CustomerContactDto> contacts;
    private List<CustomFieldValueDto> customFields;
}
