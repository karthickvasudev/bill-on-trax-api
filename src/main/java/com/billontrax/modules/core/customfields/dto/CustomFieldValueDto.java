package com.billontrax.modules.core.customfields.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Data
public class CustomFieldValueDto {
    private Long id;
    @NotNull
    private Long customFieldId;
    @NotNull
    private Long recordId;
    private String value;
}
