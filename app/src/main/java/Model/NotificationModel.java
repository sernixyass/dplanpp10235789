package Model;

public class NotificationModel {

    String title;
    String bodyMessage;

    public NotificationModel(String title, String bodyMessage) {
        this.title = title;
        this.bodyMessage = bodyMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }
}
