package com.billontrax.modules.commontable;

import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.models.ApiResponse;
import com.billontrax.common.models.ResponseStatus;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/common-table")
@AllArgsConstructor
public class CommonTableController {
    private final CommonTableService commonTableService;
    @GetMapping("{name}")
    public ApiResponse<List<TableConfig>> getTableHeaders(@PathVariable("name") String tableName) {
        ApiResponse<List<TableConfig>> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "table headers retrieved successfully"));
        response.setData(commonTableService.fetchTableHeaders(tableName));
        return response;
    }
}
