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
 private String postID;
 private String accountIDPostedIt;
 private String accountIDJoining1;
 private String accountIDJoining2;
 private String accountIDJoining3;
 private String accountIDJoining4;


    private boolean isTaken;

    //private float estimatedTime;

    private LatLng tripPosition;
    private LatLng tripDestinationPosition;
    //private String accountIDTakedIt;
 //private String rideType;
 //private Date dateOfPosting;


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



 /*
 private String startingPoint ;
 private String endPoint ;
 private Integer places;
 */

    public ListItem(String startingPoint, String endPoint, Integer places,
                    String postID, LatLng tripPosition, LatLng tripDestinationPosition,
                    String accountIDPostedIt,
                    Map<String, Boolean> weekDays) {
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.places = places;
        this.postID = postID;
        this.tripPosition = tripPosition;
        this.tripDestinationPosition = tripDestinationPosition;
        this.accountIDPostedIt = accountIDPostedIt;
        this.weekDays = weekDays;
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
