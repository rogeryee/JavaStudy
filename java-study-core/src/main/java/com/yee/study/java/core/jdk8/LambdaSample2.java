package com.yee.study.java.core.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Lambda示例
 */
public class LambdaSample2 {

    public static void main(String[] args) {

        // 将集合中的元素按照 depId和type进行分组
        List<DataBean> totalStocks = new ArrayList<>();

        DataBean stock1 = new DataBean();
        stock1.setDepId(2);
        stock1.setType(2);
        stock1.setNum(2);
        totalStocks.add(stock1);

        DataBean stock2 = new DataBean();
        stock2.setDepId(2);
        stock2.setType(2);
        stock2.setNum(3);
        totalStocks.add(stock2);

        DataBean stock3 = new DataBean();
        stock3.setDepId(3);
        stock3.setType(3);
        stock3.setNum(5);
        totalStocks.add(stock3);

        DataBean stock4 = new DataBean();
        stock4.setDepId(3);
        stock4.setType(3);
        stock4.setNum(4);
        totalStocks.add(stock4);

        DataBean stock5 = new DataBean();
        stock5.setDepId(4);
        stock5.setType(4);
        stock5.setNum(10);
        totalStocks.add(stock5);

        DataBean stock6 = new DataBean();
        stock6.setDepId(4);
        stock6.setType(5);
        stock6.setNum(1);
        totalStocks.add(stock6);

        Map<String, List<DataBean>> map = totalStocks.stream().collect(Collectors.groupingBy((stock) -> stock.getDepId() + "-" + stock.getType()));

        System.out.println(map);
    }
}

class DataBean {

    private int type;

    private int depId;

    private int num;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
