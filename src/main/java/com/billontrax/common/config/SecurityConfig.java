package com.billontrax.common.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.exceptions.UnAuthorizedException;
import com.billontrax.modules.core.authentication.dto.ExtractedTokenDto;
import com.billontrax.modules.core.authentication.entities.AuthenticationDetails;
import com.billontrax.modules.core.authentication.repositories.AuthenticationDetailsRepository;
import com.billontrax.modules.core.authentication.services.JwtTokenService;
import com.billontrax.modules.core.permission.dto.UserPermissionDto;
import com.billontrax.modules.core.permission.services.PermissionService;
import com.billontrax.modules.core.user.dto.UserProfileDto;
import com.billontrax.modules.core.user.repositories.UserRepository;
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
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
	private final UserRepository userRepository;
	private final PermissionService permissionService;
	private final AuthenticationDetailsRepository authenticationDetailsRepository;
	private final JwtTokenService jwtTokenService;

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
			authorizationManagerRequestMatcherRegistry.requestMatchers("/api/auth/login/**", "/api/onboarding/**",
					"/files/**").permitAll();
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
			protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
					@NonNull FilterChain filterChain) throws ServletException, IOException {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					String token = request.getHeader("x-auth-token");
					String refreshToken = request.getHeader("x-auth-refresh-token");
					if (Strings.isEmpty(token) || Strings.isEmpty(refreshToken)) {
						doFilter(request, response, filterChain);
						return;
					}

					/* refresh token */
					if (jwtTokenService.isTokenNotExpired(refreshToken)) {

						String authId = jwtTokenService.getAuthId(token);
						if (authenticationDetailsRepository.findById(authId).isEmpty()) {
							throw new UnAuthorizedException("Access denied. Please log in to continue.");
						}
						/* access token */
						if (jwtTokenService.isTokenNotExpired(token)) {
							authenticateAndSetCurrentUser(token);
							response.setHeader("x-auth-token", token);
							response.setHeader("x-auth-refresh-token", refreshToken);
						} else {
							createNewAccessToken(authId, refreshToken, response);
						}
					} else {
						createNewAccessTokenAndRefreshToken(refreshToken, response);
					}
					response.setHeader("Access-Control-Expose-Headers", "x-auth-token,x-auth-refresh-token");
					filterChain.doFilter(request, response);
				} catch (UnAuthorizedException | UsernameNotFoundException e) {
					log.info("error in jwt validation filter:: ", e);
					response.setStatus(ResponseCode.UNAUTHORIZED.getCode());
					response.setHeader("content-type", "application/json");
					Response<Void> apiResponse = new Response<>();
					apiResponse.setStatus(new ResponseStatus(ResponseCode.UNAUTHORIZED, e.getMessage()));
					response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
				} catch (JWTDecodeException e) {
					log.info("error in jwt validation filter:: ", e);
					response.setStatus(ResponseCode.SERVER_ERROR.getCode());
					response.setHeader("content-type", "application/json");
					Response<Void> apiResponse = new Response<>();
					apiResponse.setStatus(new ResponseStatus(ResponseCode.SERVER_ERROR));
					response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
				} finally {
					CurrentUserHolder.clear();
				}
			}

			@Override
			protected boolean shouldNotFilter(HttpServletRequest request) {
				String uri = request.getRequestURI();
				return uri.equals("/api/auth/login") || uri.startsWith("/api/onboarding") || uri.startsWith("/files/");
			}
		};
	}

	private void createNewAccessTokenAndRefreshToken(String refreshToken, HttpServletResponse response) {
		ExtractedTokenDto extractedToken = jwtTokenService.getRefreshTokenDetails(refreshToken);
		authenticationDetailsRepository.deleteByUserIdAndBusinessId(extractedToken.getUserId(),
				extractedToken.getBusinessId());
		AuthenticationDetails authenticationDetails = new AuthenticationDetails();
		authenticationDetails.setUserId(extractedToken.getUserId());
		authenticationDetails.setBusinessId(extractedToken.getBusinessId());
		AuthenticationDetails saved = authenticationDetailsRepository.save(authenticationDetails);
		String accessToken = jwtTokenService.generateAccessToken(saved.getId());
		String newRefreshToken = jwtTokenService.generateRefreshToken(saved.getId(), extractedToken.getUserId(),
				extractedToken.getBusinessId());
		authenticateAndSetCurrentUser(saved.getId());
		response.setHeader("x-auth-token", accessToken);
		response.setHeader("x-auth-refresh-token", newRefreshToken);
	}

	private void authenticateAndSetCurrentUser(String token) {
		String authId = jwtTokenService.getAuthId(token);
		AuthenticationDetails authenticationDetails = authenticationDetailsRepository.findById(authId)
				.orElseThrow(() -> new UsernameNotFoundException("Access denied. Please log in to continue."));
		UserProfileDto userProfileDto = userRepository.fetchUserProfileById(authenticationDetails.getUserId(),
						authenticationDetails.getBusinessId())
				.orElseThrow(() -> new UsernameNotFoundException("Access denied. Please log in to continue."));
		List<UserPermissionDto> permissions = permissionService.getPermissions(userProfileDto.getId());
		userProfileDto.setUserPermissions(permissions);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userProfileDto.getAsUserDetails(), null, userProfileDto.getAsUserDetails().getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		CurrentUserHolder.setCurrentUser(userProfileDto);
	}

	private void createNewAccessToken(String authId, String refreshToken, HttpServletResponse response) {
		authenticationDetailsRepository.deleteById(authId);
		ExtractedTokenDto extractedToken = jwtTokenService.getRefreshTokenDetails(refreshToken);
		AuthenticationDetails authenticationDetails = new AuthenticationDetails();
		authenticationDetails.setUserId(extractedToken.getUserId());
		authenticationDetails.setBusinessId(extractedToken.getBusinessId());
		AuthenticationDetails saved = authenticationDetailsRepository.save(authenticationDetails);
		String accessToken = jwtTokenService.generateAccessToken(saved.getId());
		authenticateAndSetCurrentUser(accessToken);
		response.setHeader("x-auth-token", accessToken);
		response.setHeader("x-auth-refresh-token", refreshToken);
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
			if (optionalUserProfile.isEmpty())
				throw new UsernameNotFoundException("user not found");
			UserProfileDto userProfileDto = optionalUserProfile.get();
			userProfileDto.setUserPermissions(permissionService.getPermissions(userProfileDto.getId()));
			return userProfileDto.getAsUserDetails();
		};
	}

	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
