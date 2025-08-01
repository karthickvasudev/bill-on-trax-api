package com.billontrax.common.services;

import com.billontrax.common.dtos.FileUploadDto;
import com.billontrax.common.exceptions.ErrorMessageException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileUploadService {
	@Value("${spring.profiles.default}")
	private String environment;

	public String uploadFile(Long businessId, String feature, String folder, FileUploadDto file) {
		String folderStructure = "files/%s/%s/%s".formatted(businessId, feature,
				folder.replaceAll("^/+", "").replaceAll("/+$", ""));
		return upload(folderStructure, file);
	}

	public String uploadFile(Long businessId, String feature, FileUploadDto file) {
		String folderStructure = "files/%s/%s".formatted(businessId, feature);
		return upload(folderStructure, file);
	}

	private String upload(String folderStructure, FileUploadDto file) {
		try {
			String folderWithFile = "%s/%s".formatted(folderStructure,
					System.currentTimeMillis() + "_" + file.getFileName());
			Files.createDirectories(Paths.get(folderStructure));
			Files.write(Paths.get(folderWithFile), Base64.getDecoder().decode(file.getData()));
			return environment.equals("dev") ? "http://localhost:8080/%s".formatted(folderWithFile) : null;
		} catch (Exception e) {
			throw new ErrorMessageException("Failed to upload file: %s".formatted(e.getMessage()));
		}
	}
}
