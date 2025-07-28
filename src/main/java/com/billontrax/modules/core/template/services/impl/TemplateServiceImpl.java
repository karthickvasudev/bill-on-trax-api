package com.billontrax.modules.core.template.services.impl;

import com.billontrax.modules.core.template.entities.Template;
import com.billontrax.modules.core.template.enums.TemplateName;
import com.billontrax.modules.core.template.repositories.TemplateRepository;
import com.billontrax.modules.core.template.services.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
	private final TemplateRepository templateRepository;

	@Override
	public String fetchTemplate(TemplateName templateName, boolean isSuperAdmin, Map<String, Object> parameters) {
		Template template = templateRepository.findByTemplateAndIsSuperAdmin(templateName, isSuperAdmin).orElseThrow();
		String htmlContent = template.getHtmlContent();
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
		}
		return htmlContent;
	}
}
