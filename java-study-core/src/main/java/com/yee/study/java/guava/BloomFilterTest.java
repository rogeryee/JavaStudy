package com.yee.study.java.guava;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.*;

/**
 * BloomFilter 测试
 *
 * @author Roger.Yi
 */
public class BloomFilterTest {

    private static final int insertions = 1000000;//100w


    public static void main(String[] args) {
        //初始化一个存储String数据的布隆过滤器，初始化大小为100w
        BloomFilter<String> bloomFilter =
                BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), insertions, 0.01);
        //初始化一个存储String数据的set，初始化大小为100w，做验证参考
        Set<String> sets = new HashSet<String>(insertions);
        //初始化一个存储String数据额list，初始化大小为100w
        List<String> lists = new ArrayList<String>(insertions);

        //向三个容器中初始化100w个随机唯一的字符串
        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bloomFilter.put(uuid);
            sets.add(uuid);
            lists.add(uuid);
        }

        //布隆过滤器误判的次数
        int wrongCount = 0;
        //布隆过滤器正确地次数
        int rightCount = 0;

        //随机抽取1w数据做验证
        for (int i = 0; i < 10000; i++) {
            String test = i % 100 == 0 ? lists.get(i / 100) : UUID.randomUUID().toString();
            //布隆过滤器验证通过
            if (bloomFilter.mightContain(test)) {
                if (sets.contains(test))
                    rightCount++;
                else
                    wrongCount++;
            }

        }
        System.out.println("right count : " + rightCount);
        System.out.println("wrong count : " + wrongCount);
        System.out.println("wrong rate : " + Math.round(((wrongCount * 1.0) / (9900)) * 100) + "%");
    }
}
