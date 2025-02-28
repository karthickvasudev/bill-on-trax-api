package com.billixapp.modules.user;

import com.billixapp.common.dtos.CurrentUser;
import com.billixapp.exceptions.ErrorMessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserProfileDto fetchProfile() {
        Optional<UserProfileDto> optionalUserProfile = userRepository.fetchUserProfileById(currentUser.getUserId());
        if(optionalUserProfile.isEmpty()) throw new ErrorMessageException("user not found");
        return optionalUserProfile.get();
    }

    @Override
    public void resetPassword(ResetPasswordRequest body) {
        User user = userRepository.findById(currentUser.getUserId()).orElseThrow(() -> new ErrorMessageException("user not found"));
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setIsPasswordResetRequired(false);
        userRepository.save(user);
    }
}
