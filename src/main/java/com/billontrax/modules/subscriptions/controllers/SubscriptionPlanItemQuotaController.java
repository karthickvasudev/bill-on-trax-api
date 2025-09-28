package com.billontrax.modules.subscriptions.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.subscriptions.dtos.SubscriptionPlanItemQuotaCreateRequest;
import com.billontrax.modules.subscriptions.dtos.SubscriptionPlanItemQuotaDto;
import com.billontrax.modules.subscriptions.services.SubscriptionPlanItemQuotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for subscription plan item quota management
 */
@RestController
@RequestMapping("/api/subscription/plan")
@RequiredArgsConstructor
public class SubscriptionPlanItemQuotaController {

    private final SubscriptionPlanItemQuotaService quotaService;

    @PostMapping("/{planId}/items")
    public Response<SubscriptionPlanItemQuotaDto> addItemQuota(@PathVariable Long planId,
            @Valid @RequestBody SubscriptionPlanItemQuotaCreateRequest request) {
        Response<SubscriptionPlanItemQuotaDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK_NOTIFY, "Item quota added successfully"));
        SubscriptionPlanItemQuotaDto dto = quotaService.addItemQuota(planId, request);
        response.setData(dto);
        return response;
    }

    @GetMapping("/{planId}/items")
    public Response<List<SubscriptionPlanItemQuotaDto>> getItemQuotas(@PathVariable Long planId) {
        Response<List<SubscriptionPlanItemQuotaDto>> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK, "Item quotas retrieved successfully"));
        List<SubscriptionPlanItemQuotaDto> quotas = quotaService.getItemQuotasByPlanId(planId);
        response.setData(quotas);
        return response;
    }

    @DeleteMapping("/{planId}/items/{itemId}")
    public Response<Void> deleteItemQuota(@PathVariable Long planId, @PathVariable Long itemId) {
        quotaService.deleteItemQuota(planId, itemId);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Item quota deleted successfully"));
    }
}
