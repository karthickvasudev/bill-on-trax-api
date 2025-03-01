package com.billontrax.modules.company.modals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyRequest {
    private String companyName;
    private String address;
    private String city;
    private String state;
    private String zip;
}
