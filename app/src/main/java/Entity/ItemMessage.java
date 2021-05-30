package Entity;

public class ItemMessage {
    private Message message;
    private String sendername;
    public ItemMessage() {
    }

    public ItemMessage(Message message, String sendername) {
        this.message = message;
        this.sendername = sendername;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }
}
