package com.yee.study.java.agent;

import java.math.BigDecimal;

/**
 * @author Roger.Yi
 */
public class ActionInfo {
    private String code;

    private BigDecimal score;

    private Integer count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
