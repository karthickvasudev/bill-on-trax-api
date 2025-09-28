package com.billontrax.modules.subscriptions.services;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.subscriptions.dtos.SubscriptionItemUsageCreateRequest;
import com.billontrax.modules.subscriptions.dtos.SubscriptionItemUsageDto;
import com.billontrax.modules.subscriptions.entities.CustomerSubscription;
import com.billontrax.modules.subscriptions.entities.SubscriptionItemUsage;
import com.billontrax.modules.subscriptions.entities.SubscriptionPlanItemQuota;
import com.billontrax.modules.subscriptions.mappers.SubscriptionMapper;
import com.billontrax.modules.subscriptions.repositories.SubscriptionItemUsageRepository;
import com.billontrax.modules.subscriptions.repositories.SubscriptionPlanItemQuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for subscription item usage operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionItemUsageService {

    private final SubscriptionItemUsageRepository usageRepository;
    private final SubscriptionPlanItemQuotaRepository quotaRepository;
    private final CustomerSubscriptionService customerSubscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionItemUsageDto recordUsage(SubscriptionItemUsageCreateRequest request) {
        // Validate customer subscription exists and is active
        CustomerSubscription subscription = customerSubscriptionService.getEntityById(request.getCustomerSubscriptionId());

        // Find or create usage record
        Optional<SubscriptionItemUsage> existingUsage = usageRepository
                .findByCustomerSubscriptionIdAndItemTypeAndItemId(
                        request.getCustomerSubscriptionId(),
                        request.getItemType(),
                        request.getItemId());

        SubscriptionItemUsage usage;
        if (existingUsage.isPresent()) {
            usage = existingUsage.get();
        } else {
            usage = new SubscriptionItemUsage();
            usage.setCustomerSubscription(subscription);
            usage.setItemType(request.getItemType());
            usage.setItemId(request.getItemId());
            usage.setUsedQuantity(0);
        }

        // Calculate new usage quantity
        int newQuantity;
        if (request.getIsIncrement()) {
            newQuantity = usage.getUsedQuantity() + request.getQuantity();
        } else {
            newQuantity = Math.max(0, usage.getUsedQuantity() - request.getQuantity());
        }

        // Validate against quota if incrementing
        if (request.getIsIncrement()) {
            Optional<SubscriptionPlanItemQuota> quota = quotaRepository
                    .findBySubscriptionPlanIdAndItemTypeAndItemId(
                            subscription.getSubscriptionPlan().getId(),
                            request.getItemType(),
                            request.getItemId());

            if (quota.isPresent() && newQuantity > quota.get().getAllowedQuantity()) {
                throw new ErrorMessageException(
                        String.format("Usage exceeds allowed quota. Allowed: %d, Requested: %d",
                                quota.get().getAllowedQuantity(), newQuantity));
            }
        }

        usage.setUsedQuantity(newQuantity);
        SubscriptionItemUsage savedUsage = usageRepository.save(usage);

        SubscriptionItemUsageDto dto = subscriptionMapper.toDto(savedUsage);

        // Set additional calculated fields
        Optional<SubscriptionPlanItemQuota> quota = quotaRepository
                .findBySubscriptionPlanIdAndItemTypeAndItemId(
                        subscription.getSubscriptionPlan().getId(),
                        request.getItemType(),
                        request.getItemId());

        if (quota.isPresent()) {
            dto.setAllowedQuantity(quota.get().getAllowedQuantity());
            dto.setRemainingQuantity(quota.get().getAllowedQuantity() - savedUsage.getUsedQuantity());
        }

        return dto;
    }

    @Transactional(readOnly = true)
    public List<SubscriptionItemUsageDto> getUsageByCustomerSubscriptionId(Long customerSubscriptionId) {
        // Validate subscription exists
        CustomerSubscription subscription = customerSubscriptionService.getEntityById(customerSubscriptionId);

        List<SubscriptionItemUsage> usages = usageRepository.findByCustomerSubscriptionId(customerSubscriptionId);
        List<SubscriptionItemUsageDto> usageDtos = subscriptionMapper.toUsageDtoList(usages);

        // Enrich with quota information
        for (SubscriptionItemUsageDto dto : usageDtos) {
            Optional<SubscriptionPlanItemQuota> quota = quotaRepository
                    .findBySubscriptionPlanIdAndItemTypeAndItemId(
                            subscription.getSubscriptionPlan().getId(),
                            dto.getItemType(),
                            dto.getItemId());

            if (quota.isPresent()) {
                dto.setAllowedQuantity(quota.get().getAllowedQuantity());
                dto.setRemainingQuantity(quota.get().getAllowedQuantity() - dto.getUsedQuantity());
            }
        }

        return usageDtos;
    }

    @Transactional(readOnly = true)
    public List<SubscriptionItemUsageDto> getUsageByCustomerId(Long customerId) {
        List<SubscriptionItemUsage> usages = usageRepository.findByCustomerIdAndActiveSubscription(customerId);
        return subscriptionMapper.toUsageDtoList(usages);
    }
}
