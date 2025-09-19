package com.billontrax.modules.core.customfields.service;

import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.core.customfields.enums.CustomFieldModule;
import com.billontrax.modules.core.features.entities.Features;

import java.util.List;
import java.util.Map;

public interface CustomFieldService {
    CustomFieldDefinitionDto createDefinition(CustomFieldDefinitionDto dto);

    CustomFieldDefinitionDto updateDefinition(Long id, CustomFieldDefinitionDto dto);

    void deleteDefinition(Long id);

    List<CustomFieldDefinitionDto> listDefinitions(CustomFieldModule module);

    void saveFieldValues(CustomFieldModule module, Long recordId, List<CustomFieldValueDto> values);

    void saveFieldValues(CustomFieldModule module, Long recordId, Map<String, Object> values);

    List<CustomFieldValueDto> getFieldValues(CustomFieldModule module, Long storeId, Long recordId);

    List<Features> getCustomFieldSupportedModules();

    CustomFieldDefinitionDto getDefinitionById(Long id);
}
