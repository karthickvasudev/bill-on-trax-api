package com.billontrax.modules.core.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtractedTokenDto {
    private Long userId;
    private Long businessId;
}
