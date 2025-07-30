package com.billontrax.modules.core.user.services.impl;

import com.billontrax.common.config.CurrentUserHolder;
import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.core.permission.services.PermissionService;
import com.billontrax.modules.core.user.dto.CreateUserRequest;
import com.billontrax.modules.core.user.dto.ResetPasswordRequest;
import com.billontrax.modules.core.user.dto.UpdateUserInformationRequest;
import com.billontrax.modules.core.user.dto.UserProfileDto;
import com.billontrax.modules.core.user.entities.User;
import com.billontrax.modules.core.user.repositories.UserRepository;
import com.billontrax.modules.core.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileDto fetchProfile() {
        Optional<UserProfileDto> optionalUserProfile = userRepository.fetchUserProfileById(CurrentUserHolder.getUserId(), CurrentUserHolder.getBusinessId());
        if(optionalUserProfile.isEmpty()) throw new ErrorMessageException("user not found");
        UserProfileDto userProfile = optionalUserProfile.get();
        userProfile.setUserPermissions(permissionService.getPermissions(CurrentUserHolder.getUserId()));
        return userProfile;
    }

    @Override
    public void resetPassword(ResetPasswordRequest body) {
        User user = userRepository.findById(CurrentUserHolder.getUserId()).orElseThrow(() -> new ErrorMessageException("user not found"));
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setIsPasswordResetRequired(false);
        userRepository.save(user);
    }

    @Override
    public User createUser(CreateUserRequest body) {
        if(Objects.nonNull(body.getPassword())){
            body.setPassword(passwordEncoder.encode(body.getPassword()));
        }
        User user = new User();
        user.setBusinessId(body.getBusinessId());
        user.setName(body.getName());
        user.setEmail(body.getEmail());
        user.setPassword(body.getPassword());
        user.setPhoneNumber(body.getPhoneNumber());
        user.setIsPasswordResetRequired(true);
        user.setIsDeleted(false);
        return userRepository.save(user);
    }

    @Override
    public void updateUserInformation(Long userId, UpdateUserInformationRequest body) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ErrorMessageException("user not found"));
        user.setName(body.getFirstName());
        user.setEmail(body.getEmail());
        user.setPhoneNumber(body.getPhoneNumber());
        user.setIsPasswordResetRequired(false);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(Long userId, ResetPasswordRequest body) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ErrorMessageException("user not found"));
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setIsPasswordResetRequired(false);
        userRepository.save(user);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ErrorMessageException("user not found"));
    }
}