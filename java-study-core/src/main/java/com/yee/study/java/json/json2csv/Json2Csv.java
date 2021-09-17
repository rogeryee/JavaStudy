package com.yee.study.java.json.json2csv;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.yee.study.util.json.JSON;
import net.minidev.json.JSONArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Roger.Yi
 */
public class Json2Csv {

    private JsonFileReader reader;

    public Json2Csv() {
        this.reader = new JsonFileReader();
    }

    public void generate(String configFileName, String dataFileName, String destFileName) throws Exception {
        CsvConfig config = getConfig(configFileName);
        String data = getData(dataFileName);
        JSONArray rows = getRows(data, config.getRowConfig());

        List<String> result = new ArrayList<>();
        buildHeader(config.getColumnConfig(), result);
        buildRows(rows, config.getColumnConfig(), result);

        System.out.println(result);
        genCsv(result, destFileName);
    }

    private void buildRows(JSONArray rows, List<ColumnConfig> columnConfigList, List<String> result) {
        rows.forEach(row -> {
            String rowJson = JSON.getDefault().toJSONString(row);
            List<String> oneRow = columnConfigList.stream()
                    .map(columnConfig -> buildColumn(rowJson, columnConfig))
                    .collect(Collectors.toList());
            result.add(String.join(",", oneRow) + "\n");
        });
    }

    private String buildColumn(String rowData, ColumnConfig config) {
        Object jsonObj = JsonPath.read(rowData, config.getPath());
        Object columnObj = null;
        if (jsonObj instanceof JSONArray) {
            JSONArray array = ((JSONArray) jsonObj);
            if (!array.isEmpty()) {
                columnObj = array.get(0);
            }
        } else {
            columnObj = jsonObj;
        }

        return format(columnObj, config);
    }

    private String format(Object obj, ColumnConfig config) {
        if (obj == null) {
            return "";
        }

        String result;
        if (obj instanceof Number && config.getFormat() != null) {
            result = new DecimalFormat(config.getFormat()).format(obj);
        } else {
            result = obj.toString();
        }

        if (config.getTrim() != null) {
            for (String trim : config.getTrim().split(",")) {
                result = result.replace(trim, "");
            }
        }

        return result;
    }

    private void buildHeader(List<ColumnConfig> columnConfigList, List<String> result) {
        result.add(String.join(",",
                columnConfigList.stream()
                        .map(ColumnConfig::getHeader)
                        .collect(Collectors.toList())) + "\n");
    }

    private JSONArray getRows(String data, RowConfig rowConfig) {
        ReadContext ctx = JsonPath.parse(data);
        return ctx.read(rowConfig.getPath());
    }

    private String getData(String dataFileName) throws Exception {
        return reader.read(dataFileName);
    }

    private CsvConfig getConfig(String configFileName) throws Exception {
        String jsonContent = reader.read(configFileName);
        return JSON.getDefault().parseToObject(jsonContent, CsvConfig.class);
    }

    private void genCsv(List<String> allRows, String destFileName) throws Exception {
        File csvFile;
        BufferedWriter csvWriter = null;

        try {
            csvFile = new File(destFileName);
            csvFile.createNewFile();
            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
            for (String row : allRows) {
                csvWriter.write(row);
//                csvWriter.newLine();
            }
            csvWriter.flush();
        } finally {
            csvWriter.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Json2Csv json2Csv = new Json2Csv();
        json2Csv.generate("json/csv_setting.json", "json/data.json", "/Users/cntp/Desktop/json2csv_result2.csv");
    }
}
