package com.billontrax.modules.core.user.entities;

import com.billontrax.common.entities.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "User")
@Table(name = "user")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long businessId;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String password;
    private Boolean isPasswordResetRequired;
    private Boolean isDeleted;
}
