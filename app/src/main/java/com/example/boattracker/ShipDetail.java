package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShipDetail extends AppCompatActivity {

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
                double distance = Math.sqrt(Math.pow(ship_detail_latitude - ship_detail_port_latitude,2) + Math.pow(ship_detail_longitude - ship_detail_port_longitude,2));
                Toast.makeText(getApplicationContext(), "" + distance, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
