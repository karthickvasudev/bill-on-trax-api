package com.billontrax.modules.company;

import com.billontrax.modules.company.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Entity(name = "Company")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {
    @Id
    private BigInteger id;
    private BigInteger ownerId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;
    private Boolean isDeleted;
    private Date createdOn;
    private Date updatedOn;
    @PrePersist
    public void beforeCreate() {
        this.createdOn = new Date();
        this.isDeleted = false;
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedOn = new Date();
    }
}
