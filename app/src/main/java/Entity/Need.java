package Entity;

import java.util.Date;

public class Need {

    private Integer need_id;

    private String title;

    private String text;

    private String time;

    private String state;

    private Integer reward;
    private Integer userid;
    private Integer ismultiple;

    public Need() {
    }
    public Need(Integer id) {
        this.need_id=id;
    }
    public Need(String title, String text, String time, String state) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.state = state;
    }

    public Need(String title, String text, String time, String state, Integer reward) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.state = state;
        this.reward = reward;
    }

    public Need(Integer need_id, String title, String text, String time, String state, Integer reward, Integer userid) {
        this.need_id = need_id;
        this.title = title;
        this.text = text;
        this.time = time;
        this.state = state;
        this.reward = reward;
        this.userid = userid;
    }

    public Need(String title, String text, String time, String state, Integer reward, Integer userid) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.state = state;
        this.reward = reward;
        this.userid = userid;
    }

    public Need(Integer need_id, String title, String text, String time, String state, Integer reward, Integer userid, Integer ismultiple) {
        this.need_id = need_id;
        this.title = title;
        this.text = text;
        this.time = time;
        this.state = state;
        this.reward = reward;
        this.userid = userid;
        this.ismultiple = ismultiple;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getNeedid() {
        return need_id;
    }

    public void setNeedid(Integer needid) {
        this.need_id = needid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getNeed_id() {
        return need_id;
    }

    public void setNeed_id(Integer need_id) {
        this.need_id = need_id;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Integer getIsmultiple() {
        return ismultiple;
    }

    public void setIsmultiple(Integer ismultiple) {
        this.ismultiple = ismultiple;
    }

    @Override
    public String toString() {
        return "Need{" +
                "need_id=" + need_id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", reward=" + reward +
                ", userid=" + userid +
                ", ismultiple=" + ismultiple +
                '}';
    }
}
