package com.billontrax.modules.authentication;

import com.billontrax.exceptions.ErrorMessageException;
import com.billontrax.modules.authentication.modals.LoginRequest;
import com.billontrax.modules.authentication.modals.LoginResponse;
import com.billontrax.modules.role.RoleName;
import com.billontrax.modules.user.modals.UserProfileDto;
import com.billontrax.modules.user.UserRepository;
import com.billontrax.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IAuthenticationServiceImpl implements IAuthenticationService {
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
        String accessToken = jwtTokenService.generateAccessToken(userProfile);
        String refreshToken = jwtTokenService.generateRefreshToken(userProfile);
        AuthenticationDetails details = new AuthenticationDetails();
        details.setUserId(userProfile.getId());
        details.setAccessToken(accessToken);
        details.setRefreshToken(refreshToken);
        authenticationDetailsRepository.save(details);
        return new LoginResponse(accessToken, refreshToken, 3600);
    }

    private boolean isFirstTimePassword(UserProfileDto userProfile, LoginRequest body) {
        String FIRST_TIME_LOGIN_KEY = "bill_on_trax_first_time_login";
        return userProfile.getRole().equals(RoleName.SUPER_ADMIN) && body.getPassword().equals(FIRST_TIME_LOGIN_KEY);
    }
}
