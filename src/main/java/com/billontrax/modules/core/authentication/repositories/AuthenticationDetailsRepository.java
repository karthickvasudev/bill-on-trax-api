package com.billontrax.modules.core.authentication.repositories;

import com.billontrax.modules.core.authentication.entities.AuthenticationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthenticationDetailsRepository extends JpaRepository<AuthenticationDetails, String> {

    @Transactional
    void deleteByUserIdAndBusinessId(Long userId, Long businessId);

	Optional<AuthenticationDetails> findByUserIdAndBusinessId(Long userId, Long businessId);
}
