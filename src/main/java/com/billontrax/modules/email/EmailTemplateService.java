package com.billontrax.modules.email;

import com.billontrax.config.ProfileConfig;
import com.billontrax.config.PropertyConfig;
import com.billontrax.exceptions.ErrorMessageException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;
    private final ProfileConfig profileConfig;

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
        log.info("Email\n");
        log.info("Subject: {}\n", subject);
        log.info("Body:\n {}", htmlContent);
        if (profileConfig.getDefaultProfile().equals("dev")) {
            openHtmlInLocal(htmlContent);
        } else {
            constructAndSendEmail(to, cc, bcc, subject, htmlContent);
        }
    }

    private void constructAndSendEmail(List<String> to, List<String> cc, List<String> bcc, String subject, String htmlContent) {

    }

    private void openHtmlInLocal(String htmlContent) {
        try {
            File tempFile = File.createTempFile("email-", ".html");
            tempFile.deleteOnExit();

            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(htmlContent);
            writer.close();
            log.info("html file path: {}", tempFile.getAbsolutePath());
        } catch (Exception e) {
            log.error("error when opening email template", e);
        }
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
