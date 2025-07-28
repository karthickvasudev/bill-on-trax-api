package com.billontrax.common.dtos;

import lombok.Data;

@Data
public class FileUploadDto {
	private String fileName;
	private byte[] data;
}
