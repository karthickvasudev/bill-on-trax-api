package com.billontrax.modules.subscriptions.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.subscriptions.dtos.*;
import com.billontrax.modules.subscriptions.enums.PlanType;
import com.billontrax.modules.subscriptions.services.SubscriptionPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for subscription plan management
 */
@RestController
@RequestMapping("/api/subscription/plan")
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    @PostMapping
    public Response<SubscriptionPlanDto> createPlan(@Valid @RequestBody SubscriptionPlanCreateRequest request) {
        Response<SubscriptionPlanDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK_NOTIFY, "Subscription plan created successfully"));
        SubscriptionPlanDto dto = subscriptionPlanService.createPlan(request);
        response.setData(dto);
        return response;
    }

    @GetMapping
    public Response<List<SubscriptionPlanDto>> getAllPlans(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) PlanType planType) {
        Response<List<SubscriptionPlanDto>> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK, "Plans retrieved successfully"));

        List<SubscriptionPlanDto> plans;
        if (name != null || planType != null) {
            plans = subscriptionPlanService.searchPlans(name, planType);
        } else {
            plans = subscriptionPlanService.getAllPlans();
        }

        response.setData(plans);
        return response;
    }

    @GetMapping("/{id}")
    public Response<SubscriptionPlanDto> getPlanById(@PathVariable Long id) {
        Response<SubscriptionPlanDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK, "Plan retrieved successfully"));
        SubscriptionPlanDto dto = subscriptionPlanService.getPlanById(id);
        response.setData(dto);
        return response;
    }

    @PutMapping("/{id}")
    public Response<SubscriptionPlanDto> updatePlan(@PathVariable Long id,
            @Valid @RequestBody SubscriptionPlanUpdateRequest request) {
        Response<SubscriptionPlanDto> response = new Response<>(
                ResponseStatus.of(ResponseCode.OK_NOTIFY, "Subscription plan updated successfully"));
        SubscriptionPlanDto dto = subscriptionPlanService.updatePlan(id, request);
        response.setData(dto);
        return response;
    }

    @DeleteMapping("/{id}")
    public Response<Void> deletePlan(@PathVariable Long id) {
        subscriptionPlanService.deletePlan(id);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Subscription plan deleted successfully"));
    }
}
