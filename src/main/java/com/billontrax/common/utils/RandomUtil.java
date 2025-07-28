package com.billontrax.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class RandomUtil {

	public static String getShortUniqueId(){
		byte[] encoded = Base64.getEncoder().encode(String.valueOf(System.nanoTime()).getBytes(StandardCharsets.UTF_8));
		return new String(encoded, StandardCharsets.UTF_8);
	}
}
