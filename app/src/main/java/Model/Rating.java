package Model;

public class Rating {
       private String userId;
//    private String conductorId;
    private double rateValue;
    private String comment;

    public Rating() {
    }

    public Rating(String userId,/* String conductorId,*/ double rateValue, String comment) {
       this.userId = userId;
//        this.conductorId = conductorId;
        this.rateValue = rateValue;
        this.comment = comment;
    }


//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getConductorId() {
//        return conductorId;
//    }
//
//    public void setConductorId(String conductorId) {
//        this.conductorId = conductorId;
//    }

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
