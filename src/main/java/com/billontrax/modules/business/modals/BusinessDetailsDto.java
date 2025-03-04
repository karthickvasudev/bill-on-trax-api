package com.billontrax.modules.business.modals;

import com.billontrax.modules.business.Business;
import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.user.User;
import com.billontrax.modules.user.modals.OwnerInformationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetailsDto {
    private BigInteger id;
    private String name;
    private BusinessStatus status;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private OwnerInformationDto ownerInformation;

    public BusinessDetailsDto(Business business, User user) {
        this.id = business.getId();
        this.name = business.getName();
        this.status = business.getStatus();
        this.address = business.getAddress();
        this.city = business.getCity();
        this.state = business.getState();
        this.zipCode = business.getZipcode();
        this.ownerInformation = new OwnerInformationDto(user);
    }
}
