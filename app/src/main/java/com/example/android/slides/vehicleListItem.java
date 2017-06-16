package com.example.android.slides;

/**
 * Created by athulnair on 14/06/17.
 */

public class vehicleListItem {

    private String heading;
    private String perkm;
    private String imageUrl;
    private String VehicleID;
    private String UserID;
    private String vehicleType;


    public vehicleListItem(String heading, String perkm, String imageUrl, String VehicleID, String UserID, String vehicleType) {
        this.heading = heading;
        this.perkm = perkm;
        this.imageUrl = imageUrl;
        this.VehicleID = VehicleID;
        this.UserID = UserID;
        this.vehicleType =vehicleType;
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
}
