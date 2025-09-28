package com.billontrax.modules.subscriptions.services;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.subscriptions.dtos.CustomerSubscriptionCreateRequest;
import com.billontrax.modules.subscriptions.dtos.CustomerSubscriptionDto;
import com.billontrax.modules.subscriptions.dtos.CustomerSubscriptionStatusUpdateRequest;
import com.billontrax.modules.subscriptions.entities.CustomerSubscription;
import com.billontrax.modules.subscriptions.entities.SubscriptionPlan;
import com.billontrax.modules.subscriptions.enums.SubscriptionStatus;
import com.billontrax.modules.subscriptions.mappers.SubscriptionMapper;
import com.billontrax.modules.subscriptions.repositories.CustomerSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for customer subscription operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerSubscriptionService {

    private final CustomerSubscriptionRepository customerSubscriptionRepository;
    private final SubscriptionPlanService subscriptionPlanService;
    private final SubscriptionMapper subscriptionMapper;

    public CustomerSubscriptionDto createSubscription(CustomerSubscriptionCreateRequest request) {
        // Validate only one active subscription per customer
        if (customerSubscriptionRepository.existsByCustomerIdAndStatus(
                request.getCustomerId(), SubscriptionStatus.ACTIVE)) {
            throw new ErrorMessageException("Customer already has an active subscription");
        }

        // Validate plan exists
        SubscriptionPlan plan = subscriptionPlanService.getEntityById(request.getPlanId());

        CustomerSubscription subscription = new CustomerSubscription();
        subscription.setCustomerId(request.getCustomerId());
        subscription.setSubscriptionPlan(plan);
        subscription.setStartDate(request.getStartDate());
        // Auto-calculate end date
        subscription.setEndDate(request.getStartDate().plusDays(plan.getValidityDays()));
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        CustomerSubscription savedSubscription = customerSubscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(savedSubscription);
    }

    @Transactional(readOnly = true)
    public List<CustomerSubscriptionDto> getSubscriptionsByCustomerId(Long customerId) {
        List<CustomerSubscription> subscriptions = customerSubscriptionRepository.findByCustomerId(customerId);
        return subscriptionMapper.toCustomerSubscriptionDtoList(subscriptions);
    }

    @Transactional(readOnly = true)
    public CustomerSubscriptionDto getSubscriptionById(Long id) {
        CustomerSubscription subscription = customerSubscriptionRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("Customer subscription not found with id: " + id));
        return subscriptionMapper.toDto(subscription);
    }

    @Transactional(readOnly = true)
    public List<CustomerSubscriptionDto> getAllSubscriptions() {
        List<CustomerSubscription> subscriptions = customerSubscriptionRepository.findAll();
        return subscriptionMapper.toCustomerSubscriptionDtoList(subscriptions);
    }

    public CustomerSubscriptionDto updateSubscriptionStatus(Long id, CustomerSubscriptionStatusUpdateRequest request) {
        CustomerSubscription subscription = customerSubscriptionRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("Customer subscription not found with id: " + id));

        subscription.setStatus(request.getStatus());
        CustomerSubscription updatedSubscription = customerSubscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(updatedSubscription);
    }

    @Transactional
    public void expireSubscriptions() {
        LocalDate currentDate = LocalDate.now();
        List<CustomerSubscription> expiredSubscriptions =
                customerSubscriptionRepository.findExpiredActiveSubscriptions(currentDate);

        for (CustomerSubscription subscription : expiredSubscriptions) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
        }

        customerSubscriptionRepository.saveAll(expiredSubscriptions);
    }

    @Transactional(readOnly = true)
    public CustomerSubscription getEntityById(Long id) {
        return customerSubscriptionRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("Customer subscription not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public CustomerSubscription getActiveSubscriptionByCustomerId(Long customerId) {
        return customerSubscriptionRepository.findActiveSubscriptionByCustomerId(customerId)
                .orElseThrow(() -> new ErrorMessageException("No active subscription found for customer: " + customerId));
    }
}
