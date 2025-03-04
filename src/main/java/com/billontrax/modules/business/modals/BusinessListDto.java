package com.billontrax.modules.business.modals;

import com.billontrax.modules.business.enums.BusinessStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessListDto {
    private BigInteger id;
    private String name;
    private String ownerName;
    private BusinessStatus status;
    private Date createdOn;
}
