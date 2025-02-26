package com.billixapp.modules.authentication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AuthenticationDetailsRepository extends JpaRepository<AuthenticationDetails, BigInteger> {
    Optional<AuthenticationDetails> findByAccessTokenAndRefreshTokenAndUserIdOrderByCreatedOnDesc(String accessToken, String refreshToken, BigInteger userId);
}
