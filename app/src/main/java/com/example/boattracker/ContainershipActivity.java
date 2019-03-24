package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boattracker.models.Containership;

public class ContainershipActivity extends AppCompatActivity {

    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containership);

        parseIntent();
        drawUI();
    }

    private void parseIntent() {
        final Intent intent = getIntent();

        this.containership = (Containership) intent.getSerializableExtra("containership");
    }

    private void drawUI() {
        final Containership containership = this.containership;

        final TextView ship_detail_name_view = findViewById(R.id.text_name);
        ship_detail_name_view.setText(containership.getName());

        final TextView ship_detail_type_view = findViewById(R.id.text_type);
        ship_detail_type_view.setText(containership.getType().getName());

        final Button distance_button = findViewById(R.id.button_distance);
        distance_button.setOnClickListener(v -> {
            final double distance = containership.getDistance(containership.getPort());

            String distanceToDisplay;

            if (distance >= 1000){
                distanceToDisplay = "Distance : " + Math.round(distance / 1000) + " km";
            } else {
                distanceToDisplay = "Distance : " + distance + " m";
            }

            Toast.makeText(getApplicationContext(), distanceToDisplay, Toast.LENGTH_SHORT).show();
        });

        final Button map_button = findViewById(R.id.button_map);
        map_button.setOnClickListener(v -> {
            final Intent map_intent = new Intent(getApplicationContext(), MapActivity.class);
            map_intent.putExtra("containership", containership);

            startActivity(map_intent);

            finish();
        });
    }
}