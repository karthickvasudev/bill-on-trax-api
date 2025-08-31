package com.billontrax.common.services;

import com.billontrax.common.dtos.FileUploadDto;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

import lombok.RequiredArgsConstructor;

import java.util.Base64;

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
		fileUploadDto.setData(Base64.getEncoder().encodeToString(body.getBytes()));
		fileUploadService.uploadFile(0L, "local-email", fileUploadDto);
	}

}
