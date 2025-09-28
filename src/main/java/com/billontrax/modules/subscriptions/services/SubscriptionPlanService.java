package com.billontrax.modules.subscriptions.services;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.subscriptions.dtos.*;
import com.billontrax.modules.subscriptions.entities.SubscriptionPlan;
import com.billontrax.modules.subscriptions.enums.PlanType;
import com.billontrax.modules.subscriptions.mappers.SubscriptionMapper;
import com.billontrax.modules.subscriptions.repositories.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for subscription plan operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionPlanDto createPlan(SubscriptionPlanCreateRequest request) {
        // Validate unique plan name
        if (subscriptionPlanRepository.existsByName(request.getName())) {
            throw new ErrorMessageException("Plan with name '" + request.getName() + "' already exists");
        }

        SubscriptionPlan plan = subscriptionMapper.toEntity(request);
        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(plan);
        return subscriptionMapper.toDto(savedPlan);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionPlanDto> getAllPlans() {
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findAll();
        return subscriptionMapper.toDtoList(plans);
    }

    @Transactional(readOnly = true)
    public SubscriptionPlanDto getPlanById(Long id) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("Subscription plan not found with id: " + id));
        return subscriptionMapper.toDto(plan);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionPlanDto> searchPlans(String name, PlanType planType) {
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findWithFilters(name, planType);
        return subscriptionMapper.toDtoList(plans);
    }

    public SubscriptionPlanDto updatePlan(Long id, SubscriptionPlanUpdateRequest request) {
        SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("Subscription plan not found with id: " + id));

        // Validate unique name if being updated
        if (request.getName() != null && !request.getName().equals(existingPlan.getName())) {
            if (subscriptionPlanRepository.existsByNameAndIdNot(request.getName(), id)) {
                throw new ErrorMessageException("Plan with name '" + request.getName() + "' already exists");
            }
        }

        // Use MapStruct for partial update
        subscriptionMapper.updatePlanFromRequest(request, existingPlan);

        SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(existingPlan);
        return subscriptionMapper.toDto(updatedPlan);
    }

    public void deletePlan(Long id) {
        if (!subscriptionPlanRepository.existsById(id)) {
            throw new ErrorMessageException("Subscription plan not found with id: " + id);
        }
        subscriptionPlanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public SubscriptionPlan getEntityById(Long id) {
        return subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("Subscription plan not found with id: " + id));
    }
}
