package com.billontrax.modules.warehouse.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Warehouse entity representing a physical storage location.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "warehouse", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Warehouse extends TimestampedWithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Lob
    private String address;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}

