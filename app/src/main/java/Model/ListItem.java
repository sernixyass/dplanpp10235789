package Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class ListItem {


 private String startingPoint ;
 private String endPoint ;
 private Integer places;
 private String postID;
 private String accountIDPostedIt;


    private boolean isTaken;

    //private float estimatedTime;

    private LatLng tripPosition;
    private LatLng tripDestinationPosition;
    //private String accountIDTakedIt;
 //private String rideType;
 //private Date dateOfPosting;

 /*
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

 /*
 private String startingPoint ;
 private String endPoint ;
 private Integer places;
 */

    public ListItem(String startingPoint, String endPoint,Integer places,
                    String postID,LatLng tripPosition,LatLng tripDestinationPosition,
                    String accountIDPostedIt) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.places = places;
        this.postID = postID;
        this.tripPosition = tripPosition;
        this.tripDestinationPosition = tripDestinationPosition;
        this.accountIDPostedIt = accountIDPostedIt;
    }

    public ListItem(){

    }

    public String getAccountIDPostedIt() {
        return accountIDPostedIt;
    }

    public void setAccountIDPostedIt(String accountIDPostedIt) {
        this.accountIDPostedIt = accountIDPostedIt;
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
