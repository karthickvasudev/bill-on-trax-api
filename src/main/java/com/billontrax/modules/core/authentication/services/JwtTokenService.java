package com.billontrax.modules.core.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.billontrax.modules.core.authentication.dto.ExtractedTokenDto;
import com.billontrax.common.exceptions.UnAuthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtTokenService {
    private final Algorithm algorithm = Algorithm.HMAC256("bill-on-trax bill-on-trax bill-on-trax bill-on-trax bill-on-trax");

    public String generateAccessToken(String authId){
        return JWT.create()
                .withIssuer("bill-on-trax-application")
                .withSubject(String.valueOf(authId))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date().toInstant().plus(15, ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    public String generateRefreshToken(String authId, Long id, Long businessId){
        return JWT.create()
                .withIssuer("bill-on-trax-application")
                .withSubject(authId)
                .withClaim("uid", id)
                .withClaim("bid", businessId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date().toInstant().plus(1, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public boolean isTokenNotExpired(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getExpiresAt().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getAuthId(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            throw new UnAuthorizedException("Invalid token");
        }
    }

    public ExtractedTokenDto getRefreshTokenDetails(String refreshToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(refreshToken);
            String authId = decodedJWT.getSubject();
            Long userId = decodedJWT.getClaim("uid").asLong();
            Long businessId = decodedJWT.getClaim("bid").asLong();
            if (authId == null || userId == null || businessId == null) {
                throw new UnAuthorizedException("Invalid refresh token");
            }
            return new ExtractedTokenDto(userId, businessId);
        } catch (TokenExpiredException e) {
            throw new UnAuthorizedException("Refresh token expired");
        } catch (Exception e) {
            throw new UnAuthorizedException("Invalid refresh token");
        }
    }
}
