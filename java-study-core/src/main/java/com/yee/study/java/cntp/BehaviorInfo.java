package com.yee.study.java.cntp;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Roger.Yi
 */
public class BehaviorInfo {
    private String code;

    private BigDecimal score;

    private Integer rank;

    private List<ActionInfo> actionInfoList;

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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<ActionInfo> getActionInfoList() {
        return actionInfoList;
    }

    public void setActionInfoList(List<ActionInfo> actionInfoList) {
        this.actionInfoList = actionInfoList;
    }
}
