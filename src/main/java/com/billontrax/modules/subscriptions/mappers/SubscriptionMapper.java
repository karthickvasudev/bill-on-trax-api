package com.billontrax.modules.subscriptions.mappers;

import com.billontrax.modules.subscriptions.dtos.*;
import com.billontrax.modules.subscriptions.entities.*;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper for subscription-related entities and DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

    // Subscription Plan Mappings
    @Mapping(target = "itemQuotas", ignore = true)
    SubscriptionPlanDto toDto(SubscriptionPlan entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    SubscriptionPlan toEntity(SubscriptionPlanCreateRequest request);

    List<SubscriptionPlanDto> toDtoList(List<SubscriptionPlan> entities);

    // Subscription Plan Item Quota Mappings
    @Mapping(target = "planId", source = "subscriptionPlan.id")
    SubscriptionPlanItemQuotaDto toDto(SubscriptionPlanItemQuota entity);

    List<SubscriptionPlanItemQuotaDto> toItemQuotaDtoList(List<SubscriptionPlanItemQuota> entities);

    // Customer Subscription Mappings
    @Mapping(target = "itemUsages", ignore = true)
    CustomerSubscriptionDto toDto(CustomerSubscription entity);

    List<CustomerSubscriptionDto> toCustomerSubscriptionDtoList(List<CustomerSubscription> entities);

    // Subscription Item Usage Mappings
    @Mapping(target = "customerSubscriptionId", source = "customerSubscription.id")
    @Mapping(target = "allowedQuantity", ignore = true)
    @Mapping(target = "remainingQuantity", ignore = true)
    SubscriptionItemUsageDto toDto(SubscriptionItemUsage entity);

    List<SubscriptionItemUsageDto> toUsageDtoList(List<SubscriptionItemUsage> entities);

    // Update method for partial updates
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updatePlanFromRequest(SubscriptionPlanUpdateRequest request, @MappingTarget SubscriptionPlan entity);
}
