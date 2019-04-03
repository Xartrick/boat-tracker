package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;

import java.io.Serializable;
import java.util.List;

public class ContainershipActivity extends AppCompatActivity {

    private Containership containership;
    private List<ContainershipType> containershipTypes;

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
        System.out.println(this.containership);
        this.containershipTypes = (List<ContainershipType>) intent.getExtras().getSerializable("containershipTypes");
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

        final Button edit_button = findViewById(R.id.button_edit_ship);
        edit_button.setOnClickListener(v -> {
            final Intent edit_intent = new Intent(getApplicationContext(), EditContainershipActivity.class);
            edit_intent.putExtra("containership", containership);
            Bundle bundle = new Bundle();
            bundle.putSerializable("containershipTypes", (Serializable) this.containershipTypes);
            edit_intent.putExtras(bundle);
            startActivity(edit_intent);
            finish();
        });
    }
}
