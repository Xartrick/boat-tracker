package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;
import com.example.boattracker.models.Position;
import com.google.firebase.firestore.GeoPoint;

public interface HasPosition extends IDocument {

    String POSITION = "position";

    /**
     * Get position
     *
     * @return Geo
     */
    default GeoPoint getPosition() {
        final Position p = (Position) get(POSITION);

        return p == null ? new GeoPoint(0, 0) : new GeoPoint(p.getLatitude(), p.getLongitude());
    }

    /**
     * Get latitude
     *
     * @return Latitude
     */
    default Double getLatitude() {
        return this.getPosition().getLatitude();
    }

    /**
     * Set latitude
     *
     * @param latitude Latitude
     */
    default void setLatitude(double latitude) {
        GeoPoint position = this.getPosition();

        this.setPosition(latitude, position.getLongitude());
    }

    /**
     * Get longitude
     *
     * @return Longitude
     */
    default Double getLongitude() {
        return this.getPosition().getLongitude();
    }

    /**
     * Set longitude
     *
     * @param longitude Longitude
     */
    default void setLongitude(double longitude) {
        GeoPoint position = this.getPosition();

        this.setPosition(position.getLatitude(), longitude);
    }

    /**
     * Set position (latitude and longitude)
     *
     * @param latitude Latitude
     * @param longitude Longitude
     */
    default void setPosition(double latitude, double longitude) {
        final Position position = new Position(latitude, longitude);

        this.put(POSITION, position);
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
