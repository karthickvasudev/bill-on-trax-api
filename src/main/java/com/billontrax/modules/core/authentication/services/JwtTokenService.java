package com.billontrax.modules.core.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.billontrax.modules.core.authentication.dto.ExtractedTokenDto;
import com.billontrax.common.exceptions.UnAuthorizedException;
import com.billontrax.modules.core.user.dto.UserProfileDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtTokenService {
    private final Algorithm algorithm = Algorithm.HMAC256("bill-on-trax bill-on-trax bill-on-trax bill-on-trax bill-on-trax");
    public String generateAccessToken(UserProfileDto userProfile){
        return JWT.create()
                .withIssuer("bill-on-trax-application")
                .withSubject(String.valueOf(userProfile.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date().toInstant().plus(1, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public String generateRefreshToken(UserProfileDto userProfile){
        return JWT.create()
                .withIssuer("bill-on-trax-application")
                .withSubject(String.valueOf(userProfile.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date().toInstant().plus(7, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public ExtractedTokenDto extractTokenDetails(String token){
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT verifiedToken = verifier.verify(token);
            long userId = Long.parseLong(verifiedToken.getSubject());
            return new ExtractedTokenDto(userId, verifiedToken.getExpiresAt());
        }catch (TokenExpiredException e){
            throw new UnAuthorizedException("Your has session expired. Please log in to proceed.");
        }
    }
}
