package com.billontrax.modules.email;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, BigInteger> {
    Optional<EmailTemplate> findByTemplateNameAndIsDeletedFalse(String templateName);
}
