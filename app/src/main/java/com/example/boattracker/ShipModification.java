package com.example.boattracker;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.boattracker.models.Containership;

public class ShipModification extends AppCompatActivity {

    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_modification);

        parseIntent();
        drawUI();
    }

    private void parseIntent() {
        final Intent intent = getIntent();

        this.containership = (Containership) intent.getSerializableExtra("containership");
    }

    private void drawUI() {
        final Containership containership = this.containership;

        final TextInputEditText ship_name_input = findViewById(R.id.text_name);
        ship_name_input.setText(containership.getName());

        final TextInputEditText ship_captain_name_input = findViewById(R.id.text_captain_name);
        ship_captain_name_input.setText(containership.getCaptainName());

        final TextInputEditText ship_type_input = findViewById(R.id.text_type);
        ship_type_input.setText(containership.getType().getName());

        final TextInputEditText ship_latitude_input = findViewById(R.id.text_latitude);
        ship_latitude_input.setText(containership.getLatitude().toString());

        final TextInputEditText ship_longitude_input = findViewById(R.id.text_longitude);
        ship_longitude_input.setText(containership.getLongitude().toString());

        final TextInputEditText ship_port_input = findViewById(R.id.text_port);
        ship_port_input.setText(containership.getPort().getName());

        final Button button_edit_containers = findViewById(R.id.button_edit_containers);
        button_edit_containers.setOnClickListener(v -> {
            //TODO
        });
    }
}
