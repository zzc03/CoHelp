package Entity;

public class ItemNeed {
    private Need need;
    private String userName;
    private String state;
    public ItemNeed() {
    }

    public ItemNeed(Need need, String userName, String state) {
        this.need = need;
        this.userName = userName;
        this.state = state;
    }

    public ItemNeed(Need need, String userName) {
        this.need = need;
        this.userName = userName;
    }

    public Need getNeed() {
        return need;
    }

    public void setNeed(Need need) {
        this.need = need;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ItemNeed{" +
                "need=" + need +
                ", userName='" + userName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
