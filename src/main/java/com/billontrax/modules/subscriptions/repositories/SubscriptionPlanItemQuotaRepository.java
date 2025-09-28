package com.billontrax.modules.subscriptions.repositories;

import com.billontrax.modules.subscriptions.entities.SubscriptionPlanItemQuota;
import com.billontrax.modules.subscriptions.enums.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for subscription plan item quota operations
 */
@Repository
public interface SubscriptionPlanItemQuotaRepository extends JpaRepository<SubscriptionPlanItemQuota, Long> {

    List<SubscriptionPlanItemQuota> findBySubscriptionPlanId(Long planId);

    Optional<SubscriptionPlanItemQuota> findBySubscriptionPlanIdAndItemTypeAndItemId(
            Long planId, ItemType itemType, Long itemId);

    boolean existsBySubscriptionPlanIdAndItemTypeAndItemId(
            Long planId, ItemType itemType, Long itemId);

    void deleteBySubscriptionPlanIdAndItemTypeAndItemId(
            Long planId, ItemType itemType, Long itemId);
}
