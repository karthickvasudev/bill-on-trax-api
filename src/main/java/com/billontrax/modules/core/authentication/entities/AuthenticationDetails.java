package com.billontrax.modules.core.authentication.entities;

import com.billontrax.common.entities.CreatedTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "AuthenticationDetails")
@Table(name = "authentication_details")
public class AuthenticationDetails extends CreatedTimestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long userId;
    private Long businessId;
}
