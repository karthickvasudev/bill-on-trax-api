package com.billontrax.modules.company.modals;

import com.billontrax.modules.company.Company;
import com.billontrax.modules.user.User;
import com.billontrax.modules.user.modals.OwnerInformationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailsDto {
    private BigInteger id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private OwnerInformationDto ownerInformation;

    public CompanyDetailsDto(Company company, User user) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.city = company.getCity();
        this.state = company.getState();
        this.zip = company.getZip();
        this.ownerInformation = new OwnerInformationDto(user);

    }
}
