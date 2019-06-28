package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InstantMessage {
    private String message ;
    private String author;
    private String mTime ;



    public InstantMessage(String message, String author ,String mTime) {
        this.message = message;
        this.author = author;
        this.mTime=mTime;

    }

    public InstantMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }


    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
}
