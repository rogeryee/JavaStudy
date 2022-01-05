package com.yee.study.java.cntp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roger.Yi
 */
public class JobRequestBuilder {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://10.28.133.68:9007/is_metadata?autoReconnect=true&autoReconnectForPools=true&useSSL=false";
    private String username = "insight_metadata_test";
    private String password = "is_meta_test_!!!";

    private String metaSQL = "select target_db, target_table, column_name, data_type, primary_key\n" +
            "     from is_metadata.sync_table_name\n" +
            "    where upper(source_db) = upper('%s') \n" +
            "    and upper(source_table) = upper('%s')\n" +
            "    order by column_id";

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver); // classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public List<ColumnMeta> getColumnMeta(String sourceDb, String sourceTable) throws Exception {
        PreparedStatement pstmt = getConnection().prepareStatement(String.format(metaSQL, sourceDb, sourceTable));
        ResultSet rs = pstmt.executeQuery();
        List<ColumnMeta> list = new ArrayList<>();
        while (rs.next()) {
            ColumnMeta meta = new ColumnMeta();
            meta.columnName = rs.getString("column_name");
            meta.columnDataType = convertType(rs.getString("data_type"));
            list.add(meta);
        }
        rs.close();
        return list;
    }

    private String convertType(String dataType) {
        String upperType = dataType.toUpperCase();
        if (upperType.startsWith("VARCHAR") || upperType.startsWith("STRING")) {
            return "STRING";
        } else if (upperType.startsWith("TEXT")) {
            return "STRING";
        } else if (upperType.startsWith("DATE")) {
            return "TIMESTAMP";
        } else if (upperType.startsWith("TIMESTAMP")) {
            return "TIMESTAMP";
        } else if (upperType.startsWith("CHAR")) {
            return "STRING";
        } else if (upperType.startsWith("NVARCHAR")) {
            return "STRING";
        } else if (upperType.startsWith("INT")) {
            return "INT";
        } else if (upperType.startsWith("DOUBLE")) {
            return "DOUBLE";
        } else if (upperType.startsWith("NUMERIC") || upperType.startsWith("DECIMAL") || upperType.startsWith("NUMBER") || upperType.startsWith("BIGINT")) {
            if (upperType.contains(",0")) {
                return "LONG";
            } else if (upperType.contains(",")) {
                return "DOUBLE";
            } else {
                return "LONG";
            }
        }

        throw new RuntimeException();
    }

    class ColumnMeta implements Serializable {
        String columnDataType;
        String columnName;

        public String getColumnDataType() {
            return columnDataType;
        }

        public void setColumnDataType(String columnDataType) {
            this.columnDataType = columnDataType;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
    }

    public static void main(String[] args) throws Exception {
        JobRequestBuilder builder = new JobRequestBuilder();
        List<ColumnMeta> metaList = builder.getColumnMeta("tpi_if_db", "tpi_cust_info_with_policy");
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(metaList);
        System.out.println(json);
    }
}
