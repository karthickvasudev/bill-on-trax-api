package com.billixapp.modules.authentication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Entity(name = "AuthenticationDetails")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger userId;
    private String accessToken;
    private String refreshToken;
    private Date createdOn;

    @PrePersist
    public void beforeCreate() {
        this.createdOn = new Date();
    }
}
