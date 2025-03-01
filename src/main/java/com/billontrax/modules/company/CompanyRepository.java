package com.billontrax.modules.company;

import com.billontrax.modules.company.modals.CompanyDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, BigInteger> {
    @Query("select new com.billontrax.modules.company.modals.CompanyDetailsDto(c, u) from Company c join Users u on u.id = c.ownerId where c.id = :id")
    Optional<CompanyDetailsDto> fetchCompanyDetailsById(BigInteger id);
}
