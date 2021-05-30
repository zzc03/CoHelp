package Entity;

public class Message {
    private Integer id;
    private Integer type;
    private Integer sendid;
    private Integer receiverid;
    private Integer isread;
    private String title;
    private String text;
    private String time;

    public Message() {
    }

    public Message(Integer id, Integer type, Integer sendid, Integer receiverid, Integer isread, String title, String text, String time) {
        this.id = id;
        this.type = type;
        this.sendid = sendid;
        this.receiverid = receiverid;
        this.isread = isread;
        this.title = title;
        this.text = text;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSendid() {
        return sendid;
    }

    public void setSendid(Integer sendid) {
        this.sendid = sendid;
    }

    public Integer getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(Integer receiverid) {
        this.receiverid = receiverid;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", type=" + type +
                ", sendid=" + sendid +
                ", receiverid=" + receiverid +
                ", isread=" + isread +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
