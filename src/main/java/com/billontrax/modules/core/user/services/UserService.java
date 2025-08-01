package com.billontrax.modules.core.user.services;

import com.billontrax.modules.core.user.dto.CreateUserRequest;
import com.billontrax.modules.core.user.dto.ResetPasswordRequest;
import com.billontrax.modules.core.user.dto.UpdateUserInformationRequest;
import com.billontrax.modules.core.user.dto.UserProfileDto;
import com.billontrax.modules.core.user.entities.User;

public interface UserService {
	UserProfileDto fetchProfile();

	void resetPassword(ResetPasswordRequest body);

	User createUser(CreateUserRequest body);

	User updateUserInformation(Long userId, UpdateUserInformationRequest body);

	void updatePassword(Long userId, ResetPasswordRequest body);

	User getUser(Long userId);
}
