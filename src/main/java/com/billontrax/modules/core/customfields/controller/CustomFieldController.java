package com.billontrax.modules.core.customfields.controller;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.core.customfields.enums.CustomFieldModule;
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

    @GetMapping("search/{module}")
    public Response<List<CustomFieldDefinitionDto>> listDefinitions(@PathVariable CustomFieldModule module) {
        Response<List<CustomFieldDefinitionDto>> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
        response.setData(customFieldService.listDefinitions(module));
        return response;
    }

    @PostMapping
    public Response<CustomFieldDefinitionDto> createDefinition(@Valid @RequestBody CustomFieldDefinitionDto dto) {
        Response<CustomFieldDefinitionDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.CREATED, "Create custom field definition successfully."));
        CustomFieldDefinitionDto createdDefinition = customFieldService.createDefinition(dto);
        response.setData(createdDefinition);
        return response;
    }

    @GetMapping("{id}")
    public Response<CustomFieldDefinitionDto> getDefinition(@PathVariable Long id) {
        Response<CustomFieldDefinitionDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
        CustomFieldDefinitionDto definition = customFieldService.getDefinitionById(id);
        response.setData(definition);
        return response;
    }

    @PutMapping("{id}")
    public Response<CustomFieldDefinitionDto> updateDefinition(@PathVariable Long id,
            @Valid @RequestBody CustomFieldDefinitionDto dto) {
        Response<CustomFieldDefinitionDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY,
                "Update custom field definition successfully."));
        CustomFieldDefinitionDto updatedDefinition = customFieldService.updateDefinition(id, dto);
        response.setData(updatedDefinition);
        return response;
    }

    @DeleteMapping("{id}")
    public Response<Void> deleteDefinition(@PathVariable Long id) {
        customFieldService.deleteDefinition(id);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Delete custom field definition successfully"));
    }

    @GetMapping("/supported-modules")
    public Response<List<Features>> getCustomFieldSupportedModules() {
        Response<List<Features>> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
        response.setData(customFieldService.getCustomFieldSupportedModules());
        return response;
    }
}
