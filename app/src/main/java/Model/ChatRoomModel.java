package Model;

public class ChatRoomModel {

    String title;
    String iconMessageID;

    public ChatRoomModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconMessageID() {
        return iconMessageID;
    }

    public void setIconMessageID(String iconMessageID) {
        this.iconMessageID = iconMessageID;
    }
}
