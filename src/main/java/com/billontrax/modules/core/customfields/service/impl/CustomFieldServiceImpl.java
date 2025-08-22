package com.billontrax.modules.core.customfields.service.impl;

import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.core.customfields.entity.CustomFieldDefinition;
import com.billontrax.modules.core.customfields.entity.CustomFieldType;
import com.billontrax.modules.core.customfields.entity.CustomFieldValue;
import com.billontrax.modules.core.customfields.mapper.CustomFieldDefinitionMapper;
import com.billontrax.modules.core.customfields.mapper.CustomFieldValueMapper;
import com.billontrax.modules.core.customfields.repository.CustomFieldDefinitionRepository;
import com.billontrax.modules.core.customfields.repository.CustomFieldValueRepository;
import com.billontrax.modules.core.customfields.service.CustomFieldService;
import com.billontrax.modules.core.features.entities.Features;
import com.billontrax.modules.core.features.repository.FeaturesRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomFieldServiceImpl implements CustomFieldService {

    private final CustomFieldDefinitionRepository definitionRepo;
    private final CustomFieldValueRepository valueRepo;
    private final CustomFieldDefinitionMapper definitionMapper;
    private final CustomFieldValueMapper valueMapper;
    private final FeaturesRepository featureRepository;

    @Override
    public CustomFieldDefinitionDto createDefinition(CustomFieldDefinitionDto dto) {
        CustomFieldDefinition entity = definitionMapper.toEntity(dto);
        return definitionMapper.toDto(definitionRepo.save(entity));
    }

    @Override
    public CustomFieldDefinitionDto updateDefinition(Long id, CustomFieldDefinitionDto dto) {
        CustomFieldDefinition entity = definitionRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Custom field not found"));
        entity.setFieldName(dto.getFieldName());
        entity.setFieldType(dto.getFieldType());
        entity.setIsRequired(dto.getIsRequired());
        entity.setDefaultValue(dto.getDefaultValue());
        entity.setOptions(dto.getOptions());
        return definitionMapper.toDto(definitionRepo.save(entity));
    }

    @Override
    public void deleteDefinition(Long id) {
        definitionRepo.deleteById(id);
    }

    @Override
    public List<CustomFieldDefinitionDto> listDefinitions(String module, Long storeId) {
        return definitionRepo.findByModuleAndStoreId(module, storeId)
                .stream().map(definitionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveFieldValues(String module, Long storeId, Long recordId, List<CustomFieldValueDto> values) {
        List<CustomFieldDefinition> definitions = definitionRepo.findByModuleAndStoreId(module, storeId);
        Map<Long, CustomFieldDefinition> defMap = definitions.stream()
                .collect(Collectors.toMap(CustomFieldDefinition::getId, d -> d));

        // Validate required fields and types
        Set<Long> providedFieldIds = values.stream().map(CustomFieldValueDto::getCustomFieldId)
                .collect(Collectors.toSet());
        for (CustomFieldDefinition def : definitions) {
            if (Boolean.TRUE.equals(def.getIsRequired())
                    && values.stream().noneMatch(v -> v.getCustomFieldId().equals(def.getId()))) {
                throw new ValidationException("Required field missing: " + def.getFieldName());
            }
        }

        for (CustomFieldValueDto valueDto : values) {
            CustomFieldDefinition def = defMap.get(valueDto.getCustomFieldId());
            if (def == null)
                throw new ValidationException("Invalid field id: " + valueDto.getCustomFieldId());
            validateValueType(def.getFieldType(), valueDto.getValue());
            CustomFieldValue value = valueMapper.toEntity(valueDto);
            value.setCustomField(def);
            valueRepo.save(value);
        }

        // Populate missing fields with default values
        for (CustomFieldDefinition def : definitions) {
            if (!providedFieldIds.contains(def.getId()) && def.getDefaultValue() != null) {
                CustomFieldValue value = CustomFieldValue.builder()
                        .customField(def)
                        .recordId(recordId)
                        .value(def.getDefaultValue())
                        .build();
                valueRepo.save(value);
            }
        }
    }

    @Override
    public List<CustomFieldValueDto> getFieldValues(String module, Long storeId, Long recordId) {
        List<CustomFieldDefinition> definitions = definitionRepo.findByModuleAndStoreId(module, storeId);
        List<Long> fieldIds = definitions.stream().map(CustomFieldDefinition::getId).collect(Collectors.toList());
        List<CustomFieldValue> values = valueRepo.findByRecordId(recordId).stream()
                .filter(v -> fieldIds.contains(v.getCustomField().getId()))
                .collect(Collectors.toList());
        return values.stream().map(valueMapper::toDto).collect(Collectors.toList());
    }

    private void validateValueType(CustomFieldType type, String value) {
        if (value == null)
            return;
        try {
            switch (type) {
                case NUMBER:
                    Double.parseDouble(value);
                    break;
                case DATE:
                    java.time.LocalDate.parse(value);
                    break;
                case CHECKBOX:
                    if (!("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)))
                        throw new ValidationException("Invalid checkbox value");
                    break;
                case TEXT:
                case SELECT:
                    // No validation needed
                    break;
            }
        } catch (Exception e) {
            throw new ValidationException("Invalid value for type " + type + ": " + value);
        }
    }

    @Override
    public List<Features> getCustomFieldSupportedModules() {
        return featureRepository.findAllByIsCustomFieldSupportTrue();
    }
}
