package com.billontrax.modules.core.customfields.dto;

import com.billontrax.modules.core.customfields.entity.CustomFieldType;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CustomFieldDefinitionDto {
    private Long id;

    @NotBlank
    private String module;

    @NotNull
    private Long storeId;

    @NotBlank
    private String fieldName;

    @NotNull
    private CustomFieldType fieldType;

    @NotNull
    private Boolean isRequired;

    private String defaultValue;
    private String options;
}
