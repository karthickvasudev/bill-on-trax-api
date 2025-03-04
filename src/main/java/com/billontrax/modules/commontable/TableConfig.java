package com.billontrax.modules.commontable;

import com.billontrax.modules.commontable.enums.ColumnType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TableConfig")
public class TableConfig {
    @Id
    private BigInteger id;
    private String tableName;
    private String columnName;
    private String responseNode;
    @Enumerated(EnumType.STRING)
    private ColumnType type;
    private Map<String, Object> enumDisplayValue;
    private Map<String, Object> buttonDetails;
    private Boolean isSearchable;
    private Boolean isSortable;
    private Date createdOn;
}
