package com.billontrax.modules.business;

import com.billontrax.modules.business.modals.BusinessDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, BigInteger> {
    @Query("select new com.billontrax.modules.business.modals.BusinessDetailsDto(c, u) from Business c join Users u on u.id = c.ownerId where c.id = :id")
    Optional<BusinessDetailsDto> fetchBusinessDetailsById(BigInteger id);
}
