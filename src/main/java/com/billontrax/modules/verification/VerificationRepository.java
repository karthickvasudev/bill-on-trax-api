package com.billontrax.modules.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, BigInteger> {
    @Query("select v from Verifications v where v.token = :token and v.isCompleted = false and v.isDeleted = false")
    Optional<Verification> fetchByToken(String token);

    @Query("select v from Verifications v where v.verificationType = :type and v.token = :token and v.isCompleted = false and v.isDeleted = false")
    Optional<Verification> fetchByTypeAndToken(String type, String token);

    List<Verification> findAllByVerificationType(String verificationType);
}
