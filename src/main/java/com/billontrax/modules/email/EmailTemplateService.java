package com.billontrax.modules.email;

import com.billontrax.exceptions.ErrorMessageException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;

    public void sendEmail(String templateName, Map<String, Object> params, List<String> to) {
        sendEmail(templateName, params, to, new ArrayList<>(), new ArrayList<>());
    }

    public void sendEmail(String templateName, Map<String, Object> params, List<String> to, List<String> ccOrBcc, boolean isBcc) {
        List<String> cc = new ArrayList<>();
        List<String> bcc = new ArrayList<>();
        if (isBcc) {
            bcc.addAll(ccOrBcc);
        } else {
            cc.addAll(ccOrBcc);
        }
        sendEmail(templateName, params, to, cc, bcc);
    }

    private void sendEmail(String templateName, Map<String, Object> params, List<String> to, List<String> cc, List<String> bcc) {
        EmailTemplate emailTemplate = findEmailTemplate(templateName);
        String subject = replaceContent(emailTemplate.getSubject(), params);
        String htmlContent = replaceContent(emailTemplate.getHtmlContent(), params);
        //TODO: Implement actual email sending logic here. Using the subject, htmlContent, to, cc and bcc lists.
    }

    private EmailTemplate findEmailTemplate(String templateName) {
        return emailTemplateRepository.findByTemplateNameAndIsDeletedFalse(templateName)
                .orElseThrow(() -> new ErrorMessageException("Email template not found with name: " + templateName));
    }

    private String replaceContent(String content, Map<String, Object> params) {
        if (content == null || params == null) {
            return content;
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if(content.contains(placeholder)){
                content = content.replace(placeholder, String.valueOf(entry.getValue()));
            }
        }
        return content;
    }
}
