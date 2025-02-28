package com.billixapp.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.billixapp.common.dtos.ApiResponse;
import com.billixapp.common.dtos.CurrentUser;
import com.billixapp.common.dtos.ExtractedTokenDto;
import com.billixapp.common.dtos.ResponseStatus;
import com.billixapp.common.enums.ResponseCode;
import com.billixapp.exceptions.UnAuthorizedException;
import com.billixapp.modules.authentication.AuthenticationDetails;
import com.billixapp.modules.authentication.AuthenticationDetailsRepository;
import com.billixapp.modules.user.UserProfileDto;
import com.billixapp.modules.user.UserRepository;
import com.billixapp.security.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final UserRepository userRepository;
    private final AuthenticationDetailsRepository authenticationDetailsRepository;
    private final JwtTokenService jwtTokenService;
    private final CurrentUser currentUser;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("*"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                return configuration;
            });
        });
        security.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/auth/login/**").permitAll();
            authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
        });
        security.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        security.addFilterBefore(jwtTokenValidator(), UsernamePasswordAuthenticationFilter.class);
        security.authenticationProvider(getAuthenticationProvider());
        return security.build();
    }


    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    protected OncePerRequestFilter jwtTokenValidator() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Date currentDate = new Date();
                    String token = request.getHeader("x-auth-token");
                    String refreshToken = request.getHeader("x-auth-refresh-token");
                    if (token == null || refreshToken == null || Strings.isEmpty(token) || Strings.isEmpty(refreshToken)) {
                        throw new UnAuthorizedException("Access denied. Please log in to continue.");
                    }
                    ExtractedTokenDto extractedToken = jwtTokenService.extractTokenDetails(token);
                    if (extractedToken.getExpiredAt().before(currentDate)) {
                        throw new UnAuthorizedException("Your session has expired. Please log in again to continue.");
                    }

                    Optional<AuthenticationDetails> optionalAuthenticationDetails = authenticationDetailsRepository.findByAccessTokenAndRefreshTokenAndUserIdOrderByCreatedOnDesc(token, refreshToken, extractedToken.getUserId());
                    if (optionalAuthenticationDetails.isEmpty()) {
                        throw new UnAuthorizedException("No active session found. Please log in to proceed.");
                    }

                    Optional<UserProfileDto> optionalUserProfileDto = userRepository.fetchUserProfileById(extractedToken.getUserId());
                    if (optionalUserProfileDto.isEmpty()) {
                        throw new UnAuthorizedException("Sorry something went wrong please contact system admin.");
                    }
                    UserProfileDto userProfileDto = optionalUserProfileDto.get();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userProfileDto.getAsUserDetails(), null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    setCurrentUserInformation(userProfileDto);
                    filterChain.doFilter(request, response);
                } catch (UnAuthorizedException e) {
                    log.info("error in jwt validation filter:: ", e);
                    response.setStatus(ResponseCode.UNAUTHORIZED.getCode());
                    response.setHeader("content-type", "application/json");
                    ApiResponse<Void> apiResponse = new ApiResponse<>();
                    apiResponse.setStatus(new ResponseStatus(ResponseCode.UNAUTHORIZED, e.getMessage()));
                    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
                } catch (JWTDecodeException e) {
                    log.info("error in jwt validation filter:: ", e);
                    response.setStatus(ResponseCode.SERVER_ERROR.getCode());
                    response.setHeader("content-type", "application/json");
                    ApiResponse<Void> apiResponse = new ApiResponse<>();
                    apiResponse.setStatus(new ResponseStatus(ResponseCode.SERVER_ERROR));
                    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
                }
            }

            @Override
            protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
                return request.getRequestURI().equals("/api/auth/login");
            }
        };
    }

    private void setCurrentUserInformation(UserProfileDto userProfileDto) {
        currentUser.setUserId(userProfileDto.getId());
        currentUser.setRoleName(userProfileDto.getRole());
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(getUserDetailsService());
        return provider;
    }

    @Bean
    protected UserDetailsService getUserDetailsService() {
        return username -> {
            Optional<UserProfileDto> optionalUserProfile = userRepository.fetchUserProfile(username);
            if (optionalUserProfile.isEmpty()) throw new UsernameNotFoundException("user not found");
            return optionalUserProfile.get().getAsUserDetails();
        };
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
