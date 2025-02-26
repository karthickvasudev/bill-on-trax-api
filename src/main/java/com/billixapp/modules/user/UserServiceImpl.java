package com.billixapp.modules.user;

import com.billixapp.common.dtos.CurrentUser;
import com.billixapp.exceptions.ErrorMessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    @Override
    public UserProfileDto fetchProfile() {
        Optional<UserProfileDto> optionalUserProfile = userRepository.fetchUserProfileById(currentUser.getUserId());
        if(optionalUserProfile.isEmpty()) throw new ErrorMessageException("user not found");
        return optionalUserProfile.get();
    }
}
