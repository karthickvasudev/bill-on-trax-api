package com.billontrax.common.services;

import com.billontrax.common.dtos.FileUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

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

	@SneakyThrows
	private String upload(String folderStructure, FileUploadDto file) {
		String folderWithFile = "%s/%s".formatted(folderStructure, file.getFileName());
		Files.createDirectories(Paths.get(folderStructure));
		Files.write(Paths.get(folderWithFile), file.getData());
		return environment.equals("dev") ? "http://localhost:8080/%s".formatted(folderWithFile) : null;
	}
}
