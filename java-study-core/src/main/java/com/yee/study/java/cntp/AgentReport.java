package com.yee.study.java.cntp;

import java.util.List;

/**
 * @author Roger.Yi
 */
public class AgentReport {
    @Attribute(raw = "id")
    private Long id;

    @Attribute(raw = "agent_code")
    private String agentCode;

    @Nested(property = "overall", attrs = {
            @Attribute(property = "score", raw = "overall_score"),
            @Attribute(property = "rank", raw = "overall_rank")})
    private BehaviorInfo overall;

//    @NestedCollection(property = "behaviorInfoList", group = "sr",
//            attrs = {
//                    @Attribute(src = "code", dest = "sr"),
//                    @Attribute(src = "sr_score", dest = "score"),
//                    @Attribute(src = "sr_rank", dest = "rank")}
//                    )
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
