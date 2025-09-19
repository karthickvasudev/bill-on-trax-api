package com.billontrax.modules.core.customfields.dto;

import com.billontrax.modules.core.customfields.entity.CustomFieldType;
import com.billontrax.modules.core.customfields.enums.CustomFieldModule;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Data
public class CustomFieldDefinitionDto {
    private Long id;
    @NotNull(message = "module must not be null")
    private CustomFieldModule module;
    @NotBlank(message = "fieldName must not be blank")
    private String fieldName;
    @NotNull
    private CustomFieldType fieldType;
    @NotNull(message = "isRequired must not be null")
    private Boolean isRequired;
    private String additionalOptions;
    private Date createdTime;
    private Date updatedTime;
}
