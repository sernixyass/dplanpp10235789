package Model;

public class NotificationModel {

    String title;
    String bodyMessage;
    String iconUserID;
    String time;

    public NotificationModel(String title, String bodyMessage,String iconUserID,String time) {
        this.title = title;
        this.bodyMessage = bodyMessage;
        this.iconUserID = iconUserID;
        this.time = time;
    }

    public String getTime() {
        return time;
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
