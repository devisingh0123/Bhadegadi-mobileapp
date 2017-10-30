package in.shreesaiconsultancy.android.obhadegadi;

/**
 * Created by athulnair on 19/07/17.
 */

public class vehicleResultItem {

    private String heading;
    private String perkm;
    private String imageUrl;
    private String VehicleID;
    private String UserID;
    private String vehicleType;
    private String state;
    private String city;
    private String verified;


    public vehicleResultItem(String heading, String perkm, String imageUrl, String VehicleID, String UserID, String vehicleType, String state, String city, String verified) {
        this.heading = heading;
        this.perkm = perkm;
        this.imageUrl = imageUrl;
        this.VehicleID = VehicleID;
        this.UserID = UserID;
        this.vehicleType =vehicleType;
        this.state = state;
        this.city = city;
        this.verified = verified;
    }

    public String getHeading() {
        return heading;
    }

    public String getPerkm() {
        return perkm;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVehicleID() { return VehicleID; }

    public String getUserID() { return UserID; }

    public String getVehicleType() { return vehicleType; }

    public String getState() { return state; }

    public String getCity() { return city; }

    public String getVerified() { return verified; }

}
