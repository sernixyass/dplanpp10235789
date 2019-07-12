package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InstantMessage {
    private String message ;
    private String author;
    private String time ;
    private String icon;



    public InstantMessage(String message, String author ,String time,String icon) {
        this.message = message;
        this.author = author;
        this.time=time;
        this.icon = icon;
    }

    public InstantMessage() { }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
