package com.billontrax.modules.core.authentication.repositories;

import com.billontrax.modules.core.authentication.entities.AuthenticationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AuthenticationDetailsRepository extends JpaRepository<AuthenticationDetails, BigInteger> {
    Optional<AuthenticationDetails> findByAccessTokenAndRefreshTokenAndUserIdOrderByCreatedTimeDesc(String accessToken, String refreshToken, Long userId);
}
