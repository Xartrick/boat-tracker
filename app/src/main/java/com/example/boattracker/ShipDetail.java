package com.example.boattracker;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShipDetail extends AppCompatActivity {

    private double getDistanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2){

        double R = 6371e3; // metres
        double f1 = Math.toRadians(lat1);
        double f2 = Math.toRadians(lat2);
        double df = Math.toRadians(lat2-lat1);
        double dl = Math.toRadians(lon2-lon1);

        double a = Math.sin(df/2) * Math.sin(df/2) +
                Math.cos(f1) * Math.cos(f2) *
                        Math.sin(dl/2) * Math.sin(dl/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_detail);

        Intent myIntent = getIntent();

        String ship_detail_name = myIntent.getStringExtra("ship_detail_name");
        TextView ship_detail_name_view = findViewById(R.id.ship_detail_name);
        ship_detail_name_view.setText(ship_detail_name);

        String ship_detail_type = myIntent.getStringExtra("ship_detail_type");
        TextView ship_detail_type_view = findViewById(R.id.ship_detail_type);
        ship_detail_type_view.setText(ship_detail_type);


        final double ship_detail_latitude = myIntent.getDoubleExtra("ship_detail_latitude", 0);
        final double ship_detail_longitude = myIntent.getDoubleExtra("ship_detail_longitude", 0);
        final double ship_detail_port_latitude = myIntent.getDoubleExtra("ship_detail_port_latitude", 0);
        final double ship_detail_port_longitude = myIntent.getDoubleExtra("ship_detail_port_longitude", 0);

        Button calculateDistance = findViewById(R.id.button_calculate_distance);
        calculateDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double distance = getDistanceInKmBetweenEarthCoordinates(ship_detail_latitude, ship_detail_longitude, ship_detail_port_latitude, ship_detail_port_longitude);
                String distanceToDisplay;
                if(distance >= 1000){
                    distanceToDisplay = "Distance : " + Math.round(distance / 1000) + " km";
                }
                else{
                    distanceToDisplay = "Distance : " + distance + " m";
                }
                Toast.makeText(getApplicationContext(), distanceToDisplay, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
