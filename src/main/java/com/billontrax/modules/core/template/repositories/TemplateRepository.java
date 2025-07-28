package com.billontrax.modules.core.template.repositories;

import com.billontrax.modules.core.template.entities.Template;
import com.billontrax.modules.core.template.enums.TemplateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {
	Optional<Template> findByTemplateAndIsSuperAdmin(TemplateName templateName, Boolean isSuperAdmin);
}
