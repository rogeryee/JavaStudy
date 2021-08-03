package com.yee.study.java.json.json2csv;

import lombok.Data;

import java.util.List;

/**
 * @author Roger.Yi
 */
@Data
public class CsvConfig {

    private RowConfig rowConfig;

    private List<ColumnConfig> columnConfig;
}
