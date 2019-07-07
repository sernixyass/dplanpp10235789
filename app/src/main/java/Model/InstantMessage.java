package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InstantMessage {
    private String message ;
    private String author;
    private String time ;



    public InstantMessage(String message, String author ,String time) {
        this.message = message;
        this.author = author;
        this.time=time;
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
}
