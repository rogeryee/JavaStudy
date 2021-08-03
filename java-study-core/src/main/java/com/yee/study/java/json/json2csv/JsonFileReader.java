package com.yee.study.java.json.json2csv;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Json文件读取器
 *
 * @author Roger.Yi
 */
public class JsonFileReader {

    public String read(String fileName) throws Exception {
        Resource resource = new ClassPathResource(fileName);
        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        StringBuffer sb = new StringBuffer();
        String data = null;
        while ((data = br.readLine()) != null) {
            sb.append(data).append("\n");
        }

        br.close();
        isr.close();
        is.close();
        return sb.toString();
    }
}
