package com.billontrax.modules.user;

import com.billontrax.common.models.CurrentUser;
import com.billontrax.exceptions.ErrorMessageException;
import com.billontrax.modules.user.modals.CreateUserRequest;
import com.billontrax.modules.user.modals.ResetPasswordRequest;
import com.billontrax.modules.user.modals.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
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

    @Override
    public User createUser(CreateUserRequest body) {
        if(Objects.nonNull(body.getPassword())){
            body.setPassword(passwordEncoder.encode(body.getPassword()));
        }
        User user = new User();
        user.setFirstName(body.getName());
        user.setLastName(null);
        user.setEmail(body.getEmail());
        user.setPassword(body.getPassword());
        user.setPhoneNumber(body.getPhoneNumber());
        user.setIsPasswordResetRequired(true);
        return userRepository.save(user);
    }
}
