package com.billontrax.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtractedTokenDto {
    private BigInteger userId;
    private Date expiredAt;
}
