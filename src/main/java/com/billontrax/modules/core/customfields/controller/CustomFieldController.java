package com.billontrax.modules.core.customfields.controller;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.core.customfields.service.CustomFieldService;
import com.billontrax.modules.core.features.entities.Features;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custom-fields")
@RequiredArgsConstructor
public class CustomFieldController {

    private final CustomFieldService customFieldService;

    @PostMapping("/definition")
    public Response<CustomFieldDefinitionDto> createDefinition(@Valid @RequestBody CustomFieldDefinitionDto dto) {
        CustomFieldDefinitionDto createdDefinition = customFieldService.createDefinition(dto);
        Response<CustomFieldDefinitionDto> response = new Response<>();
        response.setData(createdDefinition);
        return response;
    }

    @PutMapping("/definition/{id}")
    public Response<CustomFieldDefinitionDto> updateDefinition(@PathVariable Long id,
            @Valid @RequestBody CustomFieldDefinitionDto dto) {
        CustomFieldDefinitionDto updatedDefinition = customFieldService.updateDefinition(id, dto);
        Response<CustomFieldDefinitionDto> response = new Response<>();
        response.setData(updatedDefinition);
        return response;
    }

    @DeleteMapping("/definition/{id}")
    public Response<Void> deleteDefinition(@PathVariable Long id) {
        customFieldService.deleteDefinition(id);
        Response<Void> response = new Response<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK));
        return response;
    }

    @GetMapping("/definition")
    public Response<List<CustomFieldDefinitionDto>> listDefinitions(@RequestParam String module,
            @RequestParam Long storeId) {
        List<CustomFieldDefinitionDto> definitions = customFieldService.listDefinitions(module, storeId);
        Response<List<CustomFieldDefinitionDto>> response = new Response<>();
        response.setData(definitions);
        return response;
    }

    @PostMapping("/values")
    public Response<Void> saveFieldValues(
            @RequestParam String module,
            @RequestParam Long storeId,
            @RequestParam Long recordId,
            @Valid @RequestBody List<CustomFieldValueDto> values) {
        customFieldService.saveFieldValues(module, storeId, recordId, values);
        Response<Void> response = new Response<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK));
        return response;
    }

    @GetMapping("/values")
    public Response<List<CustomFieldValueDto>> getFieldValues(
            @RequestParam String module,
            @RequestParam Long storeId,
            @RequestParam Long recordId) {
        List<CustomFieldValueDto> fieldValues = customFieldService.getFieldValues(module, storeId, recordId);
        Response<List<CustomFieldValueDto>> response = new Response<>();
        response.setData(fieldValues);
        return response;
    }

    @GetMapping("/supported-modules")
    public Response<List<Features>> getCustomFieldSupportedModules() {
        Response<List<Features>> response = new Response<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK));
        response.setData(customFieldService.getCustomFieldSupportedModules());
        return response;
    }
}
