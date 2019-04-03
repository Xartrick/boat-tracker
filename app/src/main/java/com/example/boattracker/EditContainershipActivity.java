package com.example.boattracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;
import com.example.boattracker.models.Port;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditContainershipActivity extends AppCompatActivity {

    private Containership containership;
    private List<ContainershipType> containershipTypes;
    private List<Port> ports;

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
        this.containershipTypes = (List<ContainershipType>) intent.getExtras().getSerializable("containershipTypes");
        this.ports = (List<Port>) intent.getExtras().getSerializable("ports");
    }

    private void drawUI() {
        final Containership containership = this.containership;

        final TextInputEditText ship_name_input = findViewById(R.id.text_name);
        ship_name_input.setText(containership.getName());

        final TextInputEditText ship_captain_name_input = findViewById(R.id.text_captain_name);
        ship_captain_name_input.setText(containership.getCaptainName());

//        final TextInputEditText ship_type_input = findViewById(R.id.text_type);
//        ship_type_input.setText(containership.getType().getName());
        Spinner spinner_type = (Spinner) findViewById(R.id.spinner_type);
        List<String> typeNames = new ArrayList<>();
        for (int i = 0; i < this.containershipTypes.size(); i++) {
            typeNames.add(this.containershipTypes.get(i).getName());
        }
        ArrayAdapter spinner_type_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, typeNames);
        spinner_type.setAdapter(spinner_type_adapter);

        final TextInputEditText ship_latitude_input = findViewById(R.id.text_latitude);
        ship_latitude_input.setText(containership.getLatitude().toString());

        final TextInputEditText ship_longitude_input = findViewById(R.id.text_longitude);
        ship_longitude_input.setText(containership.getLongitude().toString());

        Spinner spinner_port = (Spinner) findViewById(R.id.spinner_port);
        List<String> portNames = new ArrayList<>();
        for (int i = 0; i < this.ports.size(); i++) {
            portNames.add(this.ports.get(i).getName());
        }
        ArrayAdapter spinner_port_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, portNames);
        spinner_port.setAdapter(spinner_port_adapter);

        final Button button_save_changes = findViewById(R.id.button_save);
        button_save_changes.setOnClickListener(v -> {
            containership.setName(ship_name_input.getText().toString());
            containership.setCaptainName(ship_captain_name_input.getText().toString());
            containership.setLatitude(Double.parseDouble(ship_latitude_input.getText().toString()));
            containership.setLongitude(Double.parseDouble(ship_longitude_input.getText().toString()));
            containership.setType(this.containershipTypes.get(spinner_type.getSelectedItemPosition()));
            containership.setPort(this.ports.get(spinner_port.getSelectedItemPosition()));

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            final Map<String, Object> data = containership.getData();

            db
                .collection("containerships")
                .document(containership.getId())
                .update(data)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                });
        });

        final Button button_edit_containers = findViewById(R.id.button_edit_containers);
        button_edit_containers.setOnClickListener(v -> {

        });
    }
}
