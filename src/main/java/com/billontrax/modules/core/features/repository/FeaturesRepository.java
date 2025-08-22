package com.billontrax.modules.core.features.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billontrax.modules.core.features.entities.Features;

public interface FeaturesRepository extends JpaRepository<Features, Long> {
    List<Features> findAllByIsCustomFieldSupportTrue();
}
