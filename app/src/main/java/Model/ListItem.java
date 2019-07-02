package Model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListItem {


 private String startingPoint ;
 private String endPoint ;
 private Integer places;
    private Integer totalPlaces;
 private String postID;
 private String accountIDPostedIt;
 private String accountIDJoining1;
 private String accountIDJoining2;
 private String accountIDJoining3;
 private String accountIDJoining4;


 private Boolean saturday;
    private Boolean sunday;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;

    private boolean isTaken;
    private boolean isFull;

    //private float estimatedTime;

    private LatLng tripPosition;
    private LatLng tripDestinationPosition;
    private String accountIDTakedIt;

    private String hourTrip;

    private Integer price;

 //private String rideType;
 //private Date dateOfPosting;

/*
 private Map<String,Boolean> weekDays = new Map<String, Boolean>(){
     @Override
     public int size() {
         return 0;
     }

     @Override
     public boolean isEmpty() {
         return false;
     }

     @Override
     public boolean containsKey(Object key) {
         return false;
     }

     @Override
     public boolean containsValue(Object value) {
         return false;
     }


     @Override
     public Boolean get(Object key) {
         return null;
     }

     @Override
     public Boolean put( String key, Boolean value) {
         return null;
     }

     @Override
     public Boolean remove( Object key) {
         return null;
     }

     @Override
     public void putAll( @NonNull Map<? extends String, ? extends Boolean> m) {

     }

     @Override
     public void clear() {

     }


     @NonNull
     @Override
     public Set<String> keySet() {
         return null;
     }

     @NonNull
     @Override
     public Collection<Boolean> values() {
         return null;
     }

     @NonNull
     @Override
     public Set<Entry<String, Boolean>> entrySet() {
         return null;
     }

     @Override
     public Boolean replace( String key, Boolean value) {
         return null;
     }

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

    public ListItem(String startingPoint, String endPoint, Integer places,Integer totalPlaces,
                    String postID, LatLng tripPosition, LatLng tripDestinationPosition,
                    String accountIDPostedIt,

                    Boolean saturday,
                    Boolean sunday,
                    Boolean monday,
                    Boolean tuesday,
                    Boolean wednesday,
                    Boolean thursday,
                    Boolean friday,

                    Boolean isTaken,
                    Boolean isFull,

                    String accountIDTakedIt,

                    String accountIDJoining1,
                    String accountIDJoining2,
                    String accountIDJoining3,
                    String accountIDJoining4,

                    String hourTrip,
                    Integer price) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.places = places;
        this.totalPlaces = totalPlaces;
        this.postID = postID;
        this.tripPosition = tripPosition;
        this.tripDestinationPosition = tripDestinationPosition;
        this.accountIDPostedIt = accountIDPostedIt;

        this.saturday = saturday;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;

        this.isTaken = isTaken;
        this.isFull = isFull;

        this.accountIDTakedIt = accountIDTakedIt;

        this.accountIDJoining1 = accountIDJoining1;
        this.accountIDJoining2 = accountIDJoining2;
        this.accountIDJoining3 = accountIDJoining3;
        this.accountIDJoining4 = accountIDJoining4;

        this.hourTrip=hourTrip;
        this.price = price;

    }

    public String getHourTrip() {
        return hourTrip;
    }

    public void setHourTrip(String hourTrip) {
        this.hourTrip = hourTrip;
    }

    public ListItem(){

    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
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
    public Integer getTotalPlaces() {
        return totalPlaces;
    }

    public Integer getPrice() {
        return price;
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
