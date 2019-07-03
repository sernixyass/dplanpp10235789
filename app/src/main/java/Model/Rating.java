package Model;

public class Rating {
       private String userId;
//    private String conductorId;
    private double rateValue;
    private String comment;

    private String tripInfo;

    private String iconSender;

    public Rating() {
    }

    public Rating(String userId, double rateValue, String comment,String tripInfo,String iconSender) {

       this.userId = userId;
//        this.conductorId = conductorId;
        this.rateValue = rateValue;
        this.comment = comment;
        this.tripInfo = tripInfo;
        this.iconSender = iconSender;
    }

    public String getIconSender() {
        return iconSender;
    }

    public void setIconSender(String iconSender) {
        this.iconSender = iconSender;
    }

    public String getTripInfo() {
        return tripInfo;
    }

    public void setTripInfo(String tripInfo) {
        this.tripInfo = tripInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getRateValue() {
        return rateValue;
    }

    public void setRateValue(double rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
