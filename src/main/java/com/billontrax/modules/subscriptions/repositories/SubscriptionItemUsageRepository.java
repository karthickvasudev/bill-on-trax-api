package com.billontrax.modules.subscriptions.repositories;

import com.billontrax.modules.subscriptions.entities.SubscriptionItemUsage;
import com.billontrax.modules.subscriptions.enums.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for subscription item usage operations
 */
@Repository
public interface SubscriptionItemUsageRepository extends JpaRepository<SubscriptionItemUsage, Long> {

    List<SubscriptionItemUsage> findByCustomerSubscriptionId(Long customerSubscriptionId);

    Optional<SubscriptionItemUsage> findByCustomerSubscriptionIdAndItemTypeAndItemId(
            Long customerSubscriptionId, ItemType itemType, Long itemId);

    @Query("SELECT siu FROM SubscriptionItemUsage siu " +
           "JOIN siu.customerSubscription cs " +
           "WHERE cs.customerId = :customerId AND cs.status = 'ACTIVE'")
    List<SubscriptionItemUsage> findByCustomerIdAndActiveSubscription(@Param("customerId") Long customerId);

    @Query("SELECT SUM(siu.usedQuantity) FROM SubscriptionItemUsage siu " +
           "WHERE siu.customerSubscription.id = :customerSubscriptionId " +
           "AND siu.itemType = :itemType AND siu.itemId = :itemId")
    Integer getTotalUsageByCustomerSubscriptionAndItem(
            @Param("customerSubscriptionId") Long customerSubscriptionId,
            @Param("itemType") ItemType itemType,
            @Param("itemId") Long itemId);
}
