package Model;

import java.util.Date;

public class ListItem {


    /*
    private String startingPoint ;
 private String endPoint ;
 private LatLng postPosition;
 private String postID;
 private int places;
 private boolean isTaken;
 private String accountIDPostedIt;
 private String accountIDTakedIt;
 private String rideType;
 private Date dateOfPosting;
 private float estimatedTime;
 private HashMap<String,Boolean> weekDays = new HashMap<String, Boolean>(){
     {
         put("saturday",false);
         put("sunday",false);
         put("monday",false);
         put("tuesday",false);
         put("wednesday",false);
         put("thursday",false);
         put("friday",false);
     }
 } ;

     */

 private String startingPoint ;
 private String endPoint ;
 private Integer places;

    public ListItem(String startingPoint, String endPoint,Integer places) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.places = places;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
