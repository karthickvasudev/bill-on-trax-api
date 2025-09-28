package com.billontrax.modules.subscriptions.repositories;

import com.billontrax.modules.subscriptions.entities.SubscriptionPlan;
import com.billontrax.modules.subscriptions.enums.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for subscription plan operations
 */
@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    Optional<SubscriptionPlan> findByName(String name);

    List<SubscriptionPlan> findByPlanType(PlanType planType);

    @Query("SELECT sp FROM SubscriptionPlan sp WHERE " +
           "(:name IS NULL OR LOWER(sp.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:planType IS NULL OR sp.planType = :planType)")
    List<SubscriptionPlan> findWithFilters(@Param("name") String name, @Param("planType") PlanType planType);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
