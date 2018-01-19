package com.yee.study.java.guava;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roger.Yi
 */
public class PartitionListBucketTest
{
    private static final Logger logger = LoggerFactory.getLogger(PartitionListBucketTest.class);

    @Test
    public void test()
    {
        // 初始化数据
        int size = 20;
        List<String> list = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            list.add("No." + i);
        }

        // 20个元素分成3个一批处理，一共7批
        int batchSize = 3;
        PartitionListBucket<String> bucket = new PartitionListBucket<>(batchSize, items -> {
            logger.info("----------------");
            for(String item : items)
            {
                logger.info(item);
            }
        });


        bucket.addAll(list);
        bucket.flush(true);
    }
}
