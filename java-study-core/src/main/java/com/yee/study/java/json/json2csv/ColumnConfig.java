package com.yee.study.java.json.json2csv;

import lombok.Data;

/**
 * @author Roger.Yi
 */
@Data
public class ColumnConfig extends JsonPathExpression {

    private String header;

    private String format;

    private String trim;
}
