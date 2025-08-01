package com.billontrax.common.services;

import com.billontrax.common.dtos.FileUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final FileUploadService fileUploadService;
	@Value("${spring.profiles.default}")
	private String environment;

	public void sendEmail(String to, String subject, String body) {
		if (environment.equals("dev")) {
			saveEmailInLocal(body);
		} else {
			// todo send email
		}

	}

	private void saveEmailInLocal(String body) {
		FileUploadDto fileUploadDto = new FileUploadDto();
		fileUploadDto.setFileName("local-email-" + System.currentTimeMillis() + ".html");
		fileUploadDto.setData(body);
		fileUploadService.uploadFile(0L, "local-email", fileUploadDto);
	}

}
