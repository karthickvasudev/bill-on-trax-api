package com.billontrax.modules.commontable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface TableConfigRepository extends JpaRepository<TableConfig, BigInteger> {
    List<TableConfig> findAllByTableName(String tableName);
}
