package com.billontrax.modules.core.template.services;

import com.billontrax.modules.core.template.enums.TemplateName;

import java.util.Map;

public interface TemplateService {
	String fetchTemplate(TemplateName template, boolean isSuperAdmin, Map<String, Object> parameters);
}
