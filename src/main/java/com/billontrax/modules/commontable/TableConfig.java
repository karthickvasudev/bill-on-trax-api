package com.billontrax.modules.commontable;

import com.billontrax.convertors.MapStringObjectConvertor;
import com.billontrax.modules.commontable.enums.ColumnType;
import jakarta.persistence.*;
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
    @Convert(converter = MapStringObjectConvertor.class)
    private Map<String, Object> enumDisplayValue;
    @Convert(converter = MapStringObjectConvertor.class)
    private Map<String, Object> buttonDetails;
    private Boolean isSearchable;
    private Boolean isSortable;
    private Date createdOn;
}
