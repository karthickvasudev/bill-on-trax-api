package com.billontrax.modules.verification;

import com.billontrax.convertors.MapStringObjectConvertor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

@Entity(name = "Verifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String verificationType;
    private String token;
    @Convert(converter = MapStringObjectConvertor.class)
    private Map<String, Object> parameters;
    private Boolean isCompleted;
    private Boolean isDeleted;
    private Date expiredOn;
    private Date createdOn;
    private Date updatedOn;

    @PrePersist
    public void beforeCreate() {
        this.isDeleted = false;
        this.isCompleted = false;
        this.createdOn = new Date();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedOn = new Date();
    }
}
