package com.billontrax.modules.core.authentication.entities;

import com.billontrax.common.entities.CreatedTimestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "AuthenticationDetails")
@Table(name = "authentication_details")
public class AuthenticationDetails extends CreatedTimestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
