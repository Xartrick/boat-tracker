package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;

public interface HasPosition extends IDocument {

    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";

    /**
     * Get latitude
     *
     * @return Latitude
     */
    default Double getLatitude() {
        final Double latitude = (Double) get(LATITUDE);

        return latitude == null ? 0.0 : latitude;
    }

    /**
     * Set latitude
     *
     * @param latitude Latitude
     */
    default void setLatitude(double latitude) {
        put(LATITUDE, latitude);
    }

    /**
     * Get longitude
     *
     * @return Longitude
     */
    default Double getLongitude() {
        final Double longitude = (Double) get(LONGITUDE);

        return longitude == null ? 0.0 : longitude;
    }

    /**
     * Set longitude
     *
     * @param longitude Longitude
     */
    default void setLongitude(double longitude) {
        put(LONGITUDE, longitude);
    }

    /**
     * Set position (latitude and longitude)
     *
     * @param latitude Latitude
     * @param longitude Longitude
     */
    default void setPosition(double latitude, double longitude) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    /**
     * Get distance (in km) between two positionable objects
     *
     * @param object Object to calculate the distance from/to
     *
     * @return Distance between the two objects
     */
    default double getDistance(HasPosition object) {
        final double R = 6371e3;

        final double lat1 = this.getLatitude();
        final double lat2 = object.getLatitude();
        final double lon1 = this.getLongitude();
        final double lon2 = object.getLongitude();

        final double f1 = Math.toRadians(lat1);
        final double f2 = Math.toRadians(lat2);
        final double df = Math.toRadians(lat2 - lat1);
        final double dl = Math.toRadians(lon2 - lon1);

        final double a = Math.sin(df / 2) * Math.sin(df / 2) + Math.cos(f1) * Math.cos(f2) * Math.sin(dl / 2) * Math.sin(dl / 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
