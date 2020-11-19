package com.yee.study.java.agent;

import java.util.List;

/**
 * @author Roger.Yi
 */
public class AgentReport {
    private Long id;

    private String agentCode;

    private BehaviorInfo overall;

    private List<BehaviorInfo> behaviorInfoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public BehaviorInfo getOverall() {
        return overall;
    }

    public void setOverall(BehaviorInfo overall) {
        this.overall = overall;
    }

    public List<BehaviorInfo> getBehaviorInfoList() {
        return behaviorInfoList;
    }

    public void setBehaviorInfoList(List<BehaviorInfo> behaviorInfoList) {
        this.behaviorInfoList = behaviorInfoList;
    }
}
