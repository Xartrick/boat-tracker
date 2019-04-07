package com.example.boattracker;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;
import com.example.boattracker.models.Port;
import com.example.boattracker.store.ContainershipStore;
import com.example.boattracker.store.ContainershipTypeStore;
import com.example.boattracker.store.PortStore;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditContainershipActivity extends AppCompatActivity {

    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containership_edit);

        drawUI();
    }

    private void parseIntent() {

        final Intent intent = getIntent();

        final String containership_id = intent.getStringExtra("containership_id");
        this.containership = ContainershipStore.get(containership_id);
    }

    private void drawUI() {

        parseIntent();

        final Containership containership = this.containership;

        final TextInputEditText nameInput = findViewById(R.id.text_name);
        nameInput.setText(containership.getName());

        final TextInputEditText captainNameInput = findViewById(R.id.text_captain_name);
        captainNameInput.setText(containership.getCaptainName());

        final Spinner spinnerType = findViewById(R.id.spinner_type);
        final List<String> typeNames = new ArrayList<>();
        int typeId = 0;
        final List<ContainershipType> containership_types = ContainershipTypeStore.all();
        for (int i = 0; i < containership_types.size(); i++) {
            final ContainershipType type = containership_types.get(i);

            typeNames.add(containership_types.get(i).getName());

            if (type.is(containership.getType())) {
                typeId = i;
            }
        }
        final ArrayAdapter spinnerTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeNames);
        spinnerType.setAdapter(spinnerTypeAdapter);
        spinnerType.setSelection(typeId);

        final TextInputEditText latitudeInput = findViewById(R.id.text_latitude);
        latitudeInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        latitudeInput.setText(containership.getLatitude().toString());

        final TextInputEditText longitudeInput = findViewById(R.id.text_longitude);
        longitudeInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        longitudeInput.setText(containership.getLongitude().toString());

        final Spinner spinnerPort = findViewById(R.id.spinner_port);
        final List<String> portNames = new ArrayList<>();
        int portId = 0;
        final List<Port> ports = PortStore.all();
        for (int i = 0; i < ports.size(); i++) {
            final Port port = ports.get(i);

            portNames.add(port.getName());

            if (port.is(containership.getPort())) {
                portId = i;
            }
        }
        final ArrayAdapter spinnerPortAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, portNames);
        spinnerPort.setAdapter(spinnerPortAdapter);
        spinnerPort.setSelection(portId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_containership_edit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_containership_edit_save:

                final TextInputEditText input_name = findViewById(R.id.text_name);
                final TextInputEditText input_captain_name = findViewById(R.id.text_captain_name);
                final Spinner input_type = findViewById(R.id.spinner_type);
                final Spinner input_port = findViewById(R.id.spinner_port);
                final TextInputEditText input_latitude = findViewById(R.id.text_latitude);
                final TextInputEditText inputLogitude = findViewById(R.id.text_longitude);

                containership.setName(input_name.getText().toString());
                containership.setCaptainName(input_captain_name.getText().toString());
                containership.setLatitude(Double.parseDouble(input_latitude.getText().toString()));
                containership.setLongitude(Double.parseDouble(inputLogitude.getText().toString()));
                containership.setType(ContainershipTypeStore.all().get(input_type.getSelectedItemPosition()));
                containership.setPort(PortStore.all().get(input_port.getSelectedItemPosition()));

                final Map<String, Object> data = containership.getData();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db
                    .collection("containerships")
                    .document(containership.getId())
                    .update(data)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Containership edited!", Toast.LENGTH_SHORT).show();

                        onBackPressed();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to edit containership.", Toast.LENGTH_SHORT).show();
                    });

                return true;

            case android.R.id.home:

                onBackPressed();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
