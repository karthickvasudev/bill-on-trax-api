package com.billontrax.common.config;

import com.billontrax.modules.core.user.dto.UserProfileDto;
import lombok.Data;

@Data
public class CurrentUserHolder {
	private static ThreadLocal<UserProfileDto> currentUser = new ThreadLocal<>();

	public static UserProfileDto getCurrentUser() {
		return currentUser.get();
	}

	public static void setCurrentUser(UserProfileDto user) {
		currentUser.set(user);
	}

	public static void clear() {
		currentUser.remove();
	}

	public static Long getUserId() {
		UserProfileDto user = currentUser.get();
		return user != null ? user.getId() : null;
	}

	public static Long getBusinessId() {
		UserProfileDto user = currentUser.get();
		return user != null ? user.getBusinessId() : null;
	}
}
