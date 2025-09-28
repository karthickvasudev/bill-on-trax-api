package com.billontrax.modules.subscriptions.services;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.subscriptions.dtos.SubscriptionPlanItemQuotaCreateRequest;
import com.billontrax.modules.subscriptions.dtos.SubscriptionPlanItemQuotaDto;
import com.billontrax.modules.subscriptions.entities.SubscriptionPlan;
import com.billontrax.modules.subscriptions.entities.SubscriptionPlanItemQuota;
import com.billontrax.modules.subscriptions.mappers.SubscriptionMapper;
import com.billontrax.modules.subscriptions.repositories.SubscriptionPlanItemQuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for subscription plan item quota operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionPlanItemQuotaService {

    private final SubscriptionPlanItemQuotaRepository quotaRepository;
    private final SubscriptionPlanService subscriptionPlanService;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionPlanItemQuotaDto addItemQuota(Long planId, SubscriptionPlanItemQuotaCreateRequest request) {
        // Validate plan exists
        SubscriptionPlan plan = subscriptionPlanService.getEntityById(planId);

        // Validate unique combination
        if (quotaRepository.existsBySubscriptionPlanIdAndItemTypeAndItemId(
                planId, request.getItemType(), request.getItemId())) {
            throw new ErrorMessageException(
                    String.format("Item quota already exists for plan %d, item type %s, and item ID %d",
                            planId, request.getItemType(), request.getItemId()));
        }

        SubscriptionPlanItemQuota quota = new SubscriptionPlanItemQuota();
        quota.setSubscriptionPlan(plan);
        quota.setItemType(request.getItemType());
        quota.setItemId(request.getItemId());
        quota.setAllowedQuantity(request.getAllowedQuantity());

        SubscriptionPlanItemQuota savedQuota = quotaRepository.save(quota);
        return subscriptionMapper.toDto(savedQuota);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionPlanItemQuotaDto> getItemQuotasByPlanId(Long planId) {
        // Validate plan exists
        subscriptionPlanService.getEntityById(planId);

        List<SubscriptionPlanItemQuota> quotas = quotaRepository.findBySubscriptionPlanId(planId);
        return subscriptionMapper.toItemQuotaDtoList(quotas);
    }

    public void deleteItemQuota(Long planId, Long itemId) {
        // Validate plan exists
        subscriptionPlanService.getEntityById(planId);

        // Find the quota to delete
        SubscriptionPlanItemQuota quota = quotaRepository.findById(itemId)
                .orElseThrow(() -> new ErrorMessageException("Item quota not found with id: " + itemId));

        if (!quota.getSubscriptionPlan().getId().equals(planId)) {
            throw new ErrorMessageException("Item quota does not belong to the specified plan");
        }

        quotaRepository.delete(quota);
    }
}
