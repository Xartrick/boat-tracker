package com.example.boattracker.models.traits;

import android.util.Log;

import com.example.boattracker.documents.IDocument;

public interface HasLocalization extends IDocument {

    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";

    default Double getLatitude() {
        final Double latitude = (Double) get(LATITUDE);

        return latitude == null ? 0.0 : latitude;
    }

    default void setLatitude(double latitude) {
        put(LATITUDE, latitude);
    }

    default Double getLongitude() {
        final Double longitude = (Double) get(LONGITUDE);

        return longitude == null ? 0.0 : longitude;
    }

    default void setLongitude(double longitude) {
        put(LONGITUDE, longitude);
    }

    default void setLocalization(double latitude, double longitude) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    default double getDistance(HasLocalization object) {
        final double R = 6371e3;

        final double lat1 = this.getLatitude();
        final double lat2 = object.getLatitude();
        final double lon1 = this.getLongitude();
        final double lon2 = object.getLongitude();

        Log.i("DIST", "Latitude 1 : " + lat1);
        Log.i("DIST", "Latitude 2 : " + lat2);
        Log.i("DIST", "Longitude 1 : " + lon1);
        Log.i("DIST", "Longitude 2 : " + lon2);

        final double f1 = Math.toRadians(lat1);
        final double f2 = Math.toRadians(lat2);
        final double df = Math.toRadians(lat2 - lat1);
        final double dl = Math.toRadians(lon2 - lon1);

        final double a = Math.sin(df / 2) * Math.sin(df / 2) + Math.cos(f1) * Math.cos(f2) * Math.sin(dl / 2) * Math.sin(dl / 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
