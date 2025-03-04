package com.billontrax.modules.business;

import com.billontrax.modules.business.enums.BusinessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Entity(name = "Business")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger ownerId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    @Enumerated(EnumType.STRING)
    private BusinessStatus status;
    private Boolean isDeleted;
    private Date createdOn;
    private Date updatedOn;
    @PrePersist
    public void beforeCreate() {
        this.status = BusinessStatus.CREATED;
        this.createdOn = new Date();
        this.isDeleted = false;
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedOn = new Date();
    }
}
