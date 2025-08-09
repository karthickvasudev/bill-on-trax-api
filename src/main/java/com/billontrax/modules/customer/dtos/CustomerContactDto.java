package com.billontrax.modules.customer.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerContactDto {
    private Long id;
    private String name;
    @Pattern(regexp = "$|^\\d{10}$", message = "Contatct Person Phone number must be 10 digits")
    private String phone;
    private String email;
}
