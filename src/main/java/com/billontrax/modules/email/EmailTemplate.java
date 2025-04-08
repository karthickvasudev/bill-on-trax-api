package com.billontrax.modules.email;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Entity(name = "EmailTemplates")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String templateName;
    private String subject;
    @Lob
    private String htmlContent;
    private Boolean isDeleted;
    private Date createdOn;
}
