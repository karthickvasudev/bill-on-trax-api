package com.billontrax.modules.commontable;

import com.billontrax.exceptions.ErrorMessageException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommonTableService {
    private final TableConfigRepository tableConfigRepository;

    public List<TableConfig> fetchTableHeaders(String tableName){
        List<TableConfig> list = tableConfigRepository.findAllByTableName(tableName);
        if (list.isEmpty()) {
            throw new ErrorMessageException("Table " + tableName + " not found");
        }
        return list;
    }
}
