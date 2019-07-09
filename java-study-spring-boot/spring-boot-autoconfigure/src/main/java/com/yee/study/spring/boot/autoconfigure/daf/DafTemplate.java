package com.yee.study.spring.boot.autoconfigure.daf;

import com.yee.study.spring.boot.autoconfigure.datasource.DataSourceModel;

/**
 * @author Roger.Yi
 */
public class DafTemplate {

    private String id;

    private DataSourceModel dataSource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataSourceModel getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSourceModel dataSource) {
        this.dataSource = dataSource;
    }
}
