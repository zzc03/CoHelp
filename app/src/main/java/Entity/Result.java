package Entity;

import java.util.Arrays;

public class Result {

    private Integer resultId;
    private Integer needid;
    private Integer acceptuserid;
    private String state;
    private String accepttime;
    private String accepttext;
    private Integer picture;
    private Integer reward;

    public Result() {
    }

    public Result(Integer needid, Integer acceptuserid, String state, String accepttime, String accepttext, Integer picture, Integer reward) {
        this.needid = needid;
        this.acceptuserid = acceptuserid;
        this.state = state;
        this.accepttime = accepttime;
        this.accepttext = accepttext;
        this.picture = picture;
        this.reward = reward;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getNeedid() {
        return needid;
    }

    public void setNeedid(Integer needid) {
        this.needid = needid;
    }

    public Integer getAcceptuserid() {
        return acceptuserid;
    }

    public void setAcceptuserid(Integer acceptuserid) {
        this.acceptuserid = acceptuserid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAccepttime() {
        return accepttime;
    }

    public void setAccepttime(String accepttime) {
        this.accepttime = accepttime;
    }

    public String getAccepttext() {
        return accepttext;
    }

    public void setAccepttext(String accepttext) {
        this.accepttext = accepttext;
    }

    public Integer getPicture() {
        return picture;
    }

    public void setPicture(Integer picture) {
        this.picture = picture;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultId=" + resultId +
                ", needid=" + needid +
                ", acceptuserid=" + acceptuserid +
                ", state='" + state + '\'' +
                ", accepttime='" + accepttime + '\'' +
                ", accepttext='" + accepttext + '\'' +
                ", picture=" + picture +
                ", reward=" + reward +
                '}';
    }
}
