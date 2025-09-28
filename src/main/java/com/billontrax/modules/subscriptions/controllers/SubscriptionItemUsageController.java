package com.billontrax.modules.subscriptions.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.subscriptions.dtos.SubscriptionItemUsageCreateRequest;
import com.billontrax.modules.subscriptions.dtos.SubscriptionItemUsageDto;
import com.billontrax.modules.subscriptions.services.SubscriptionItemUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for subscription item usage tracking
 */
@RestController
@RequestMapping("/api/subscription/usage")
@RequiredArgsConstructor
public class SubscriptionItemUsageController {

    private final SubscriptionItemUsageService usageService;

    @PostMapping
    public Response<SubscriptionItemUsageDto> recordUsage(@Valid @RequestBody SubscriptionItemUsageCreateRequest request) {
        Response<SubscriptionItemUsageDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK_NOTIFY, "Usage recorded successfully"));
        SubscriptionItemUsageDto dto = usageService.recordUsage(request);
        response.setData(dto);
        return response;
    }

    @GetMapping("/{customerSubscriptionId}")
    public Response<List<SubscriptionItemUsageDto>> getUsageBySubscription(@PathVariable Long customerSubscriptionId) {
        Response<List<SubscriptionItemUsageDto>> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK, "Usage summary retrieved successfully"));
        List<SubscriptionItemUsageDto> usage = usageService.getUsageByCustomerSubscriptionId(customerSubscriptionId);
        response.setData(usage);
        return response;
    }

    @GetMapping("/customer/{customerId}")
    public Response<List<SubscriptionItemUsageDto>> getUsageByCustomer(@PathVariable Long customerId) {
        Response<List<SubscriptionItemUsageDto>> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK, "Customer usage retrieved successfully"));
        List<SubscriptionItemUsageDto> usage = usageService.getUsageByCustomerId(customerId);
        response.setData(usage);
        return response;
    }
}
