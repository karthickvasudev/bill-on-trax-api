package com.billontrax.modules.subscriptions.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.subscriptions.dtos.CustomerSubscriptionCreateRequest;
import com.billontrax.modules.subscriptions.dtos.CustomerSubscriptionDto;
import com.billontrax.modules.subscriptions.dtos.CustomerSubscriptionStatusUpdateRequest;
import com.billontrax.modules.subscriptions.services.CustomerSubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for customer subscription management
 */
@RestController
@RequestMapping("/api/subscription/customer")
@RequiredArgsConstructor
public class CustomerSubscriptionController {

    private final CustomerSubscriptionService customerSubscriptionService;

    @PostMapping
    public Response<CustomerSubscriptionDto> createSubscription(@Valid @RequestBody CustomerSubscriptionCreateRequest request) {
        Response<CustomerSubscriptionDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK_NOTIFY, "Customer subscription created successfully"));
        CustomerSubscriptionDto dto = customerSubscriptionService.createSubscription(request);
        response.setData(dto);
        return response;
    }

    @GetMapping
    public Response<List<CustomerSubscriptionDto>> getAllSubscriptions(
            @RequestParam(required = false) Long customerId) {
        Response<List<CustomerSubscriptionDto>> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK, "Subscriptions retrieved successfully"));

        List<CustomerSubscriptionDto> subscriptions;
        if (customerId != null) {
            subscriptions = customerSubscriptionService.getSubscriptionsByCustomerId(customerId);
        } else {
            subscriptions = customerSubscriptionService.getAllSubscriptions();
        }

        response.setData(subscriptions);
        return response;
    }

    @GetMapping("/{id}")
    public Response<CustomerSubscriptionDto> getSubscriptionById(@PathVariable Long id) {
        Response<CustomerSubscriptionDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK, "Subscription retrieved successfully"));
        CustomerSubscriptionDto dto = customerSubscriptionService.getSubscriptionById(id);
        response.setData(dto);
        return response;
    }

    @PutMapping("/{id}/status")
    public Response<CustomerSubscriptionDto> updateSubscriptionStatus(@PathVariable Long id,
            @Valid @RequestBody CustomerSubscriptionStatusUpdateRequest request) {
        Response<CustomerSubscriptionDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK_NOTIFY, "Subscription status updated successfully"));
        CustomerSubscriptionDto dto = customerSubscriptionService.updateSubscriptionStatus(id, request);
        response.setData(dto);
        return response;
    }
}
