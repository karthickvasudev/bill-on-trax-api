package com.billontrax.modules.subscriptions.repositories;

import com.billontrax.modules.subscriptions.entities.CustomerSubscription;
import com.billontrax.modules.subscriptions.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for customer subscription operations
 */
@Repository
public interface CustomerSubscriptionRepository extends JpaRepository<CustomerSubscription, Long> {

    List<CustomerSubscription> findByCustomerId(Long customerId);

    List<CustomerSubscription> findByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);

    @Query("SELECT cs FROM CustomerSubscription cs WHERE cs.customerId = :customerId AND cs.status = 'ACTIVE'")
    Optional<CustomerSubscription> findActiveSubscriptionByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT cs FROM CustomerSubscription cs WHERE cs.endDate < :currentDate AND cs.status = 'ACTIVE'")
    List<CustomerSubscription> findExpiredActiveSubscriptions(@Param("currentDate") LocalDate currentDate);

    boolean existsByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);
}
