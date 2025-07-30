package com.billontrax.modules.core.authentication.services.impl;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.core.authentication.dto.LoginRequest;
import com.billontrax.modules.core.authentication.dto.LoginResponse;
import com.billontrax.modules.core.authentication.entities.AuthenticationDetails;
import com.billontrax.modules.core.authentication.repositories.AuthenticationDetailsRepository;
import com.billontrax.modules.core.authentication.services.AuthenticationService;
import com.billontrax.modules.core.authentication.services.JwtTokenService;
import com.billontrax.modules.core.user.dto.UserProfileDto;
import com.billontrax.modules.core.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationDetailsRepository authenticationDetailsRepository;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public LoginResponse login(LoginRequest body) {
        Optional<UserProfileDto> optionalUser = userRepository.fetchUserProfile(body.getUsername());
        if (optionalUser.isEmpty()) throw new ErrorMessageException("User not found");
        UserProfileDto userProfile = optionalUser.get();
        boolean isFirstTimePassword = isFirstTimePassword(userProfile, body);
        if (!isFirstTimePassword) {
            try {
                Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));
                if (!authenticate.isAuthenticated()) {
                    throw new ErrorMessageException("Invalid username or password.");
                }
            }catch (BadCredentialsException e){
                throw new ErrorMessageException("Invalid username or password.");
            }
        }
        return generateToken(userProfile);
    }

    private LoginResponse generateToken(UserProfileDto userProfile) {
        authenticationDetailsRepository.findByUserIdAndBusinessId(userProfile.getId(), userProfile.getBusinessId())
                .ifPresent((details) -> {;
                    authenticationDetailsRepository.deleteById(details.getId());
                });
        AuthenticationDetails details = new AuthenticationDetails();
        details.setUserId(userProfile.getId());
        details.setBusinessId(userProfile.getBusinessId());
        AuthenticationDetails saved = authenticationDetailsRepository.save(details);
        String accessToken = jwtTokenService.generateAccessToken(saved.getId());
        String refreshToken = jwtTokenService.generateRefreshToken(saved.getId(), userProfile.getId(), userProfile.getBusinessId());
        return new LoginResponse(accessToken, refreshToken, 3600);
    }

    private boolean isFirstTimePassword(UserProfileDto userProfile, LoginRequest body) {
        String FIRST_TIME_LOGIN_KEY = "bill_on_trax_first_time_login";
        return userProfile.getIsSuperAdmin() && body.getPassword().equals(FIRST_TIME_LOGIN_KEY) && userProfile.getIsPasswordResetRequired();
    }
}
