package com.billixapp.modules.role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Roles")
public class Role {
    @Id
    private BigInteger id;
    @Enumerated(EnumType.STRING)
    private RoleName name;
    private Date createdOn;
    private Boolean isDeleted;
}
