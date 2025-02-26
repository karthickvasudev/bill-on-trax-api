package com.billixapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.billixapp.common.dtos.ExtractedTokenDto;
import com.billixapp.modules.user.UserProfileDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtTokenService {
    private final Algorithm algorithm = Algorithm.HMAC256("billix_billix_billix_billix_billix_billix");
    public String generateAccessToken(UserProfileDto userProfile){
        return JWT.create()
                .withIssuer("billix-application")
                .withSubject(String.valueOf(userProfile.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date().toInstant().plus(1, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public String generateRefreshToken(UserProfileDto userProfile){
        return JWT.create()
                .withIssuer("billix-application")
                .withSubject(String.valueOf(userProfile.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date().toInstant().plus(7, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public ExtractedTokenDto extractTokenDetails(String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verifiedToken = verifier.verify(token);
        BigInteger userId = BigInteger.valueOf(Long.parseLong(verifiedToken.getSubject()));
        return new ExtractedTokenDto(userId, verifiedToken.getExpiresAt());
    }
}
