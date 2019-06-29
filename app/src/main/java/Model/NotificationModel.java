package Model;

public class NotificationModel {

    String title;
    String bodyMessage;
    String iconUserID;

    public NotificationModel(String title, String bodyMessage,String iconUserID) {
        this.title = title;
        this.bodyMessage = bodyMessage;
        this.iconUserID = iconUserID;
    }

    public String getIconUserID() {
        return iconUserID;
    }

    public String getTitle() {
        return title;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

}
