package com.yee.study.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * HDFS 操作示例
 * @author Roger.Yi
 */
public class HdfsDemo {

    /**
     * $HADOOP_HOME/etc/hadoop/core.xml中HDFS的配置
     */
    private static String hdfsServerName = "fs.defaultFS";

    /**
     * $HADOOP_HOME/etc/hadoop/core.xml中HDFS的配置
     */
    private static String hdfsServerHost = "hdfs://localhost:9000";

    public static void main(String[] args) throws Exception {
        
        // 创建Configuration对象
        Configuration conf = new Configuration();
        conf.set(hdfsServerName, hdfsServerHost);

        // 创建FileSystem对象
        FileSystem fs = FileSystem.get(conf);

        // 打开文件
//        openFile(fs);

        // 下载文件
        download(fs);
    }

    private static void openFile(FileSystem fs) throws Exception {
        String filePath = "/tfiles/a.txt";
        FSDataInputStream is = fs.open(new Path(filePath));

        byte[] buff = new byte[1024];
        int length = 0;
        while ((length = is.read(buff)) != -1) {
            System.out.println(new String(buff, 0, length));
        }
    }

    private static void download(FileSystem fs) throws Exception {
        String filePath = "/tfiles/a.txt";
        String localPath = "/Users/RogerYee/";
        fs.copyToLocalFile(new Path(filePath),new Path(localPath));
    }
}
