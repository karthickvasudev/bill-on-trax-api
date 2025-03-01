package com.billontrax.modules.business.modals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusinessRequest {
    private String companyName;
    private String address;
    private String city;
    private String state;
    private String zip;
}
