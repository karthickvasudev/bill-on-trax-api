package com.billontrax.modules.core.customfields.dto;

import com.billontrax.modules.core.customfields.entity.CustomFieldType;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@Data
public class CustomFieldValueDto {
    private Long id;
	private Boolean isRequired;
    private String key;
	private CustomFieldType type;
	private Object value;
}
