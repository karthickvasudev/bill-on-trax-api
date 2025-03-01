package com.billontrax.modules.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Users")
public class User {
    @Id
    private BigInteger id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String password;
    private Boolean isPasswordResetRequired;
    private Boolean isDeleted;
    private Date createdOn;
    private Date updatedOn;

    @PrePersist
    public void beforeCreate(){
        this.createdOn = new Date();
        this.isDeleted = false;
    }

    @PreUpdate
    public void beforeUpdate(){
        this.updatedOn = new Date();
    }
}
