package com.example.boattracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Objects;

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

        containership = ContainershipStore.get(containership_id);
    }

    private void drawUI() {

        parseIntent();

        final TextInputEditText nameInput = findViewById(R.id.text_name);
        nameInput.setText(containership.getName());

        final TextInputEditText captainInput = findViewById(R.id.text_captain_name);
        captainInput.setText(containership.getCaptainName());

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

        final String latitude = containership.getLatitude().toString();
        final TextInputEditText latitudeInput = findViewById(R.id.text_latitude);
        latitudeInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        latitudeInput.setText(latitude);

        final String longitude = containership.getLongitude().toString();
        final TextInputEditText longitudeInput = findViewById(R.id.text_longitude);
        longitudeInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        longitudeInput.setText(longitude);

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

                final TextInputEditText nameInput = findViewById(R.id.text_name);
                final TextInputEditText captainInput = findViewById(R.id.text_captain_name);
                final Spinner typeInput = findViewById(R.id.spinner_type);
                final Spinner portInput = findViewById(R.id.spinner_port);
                final TextInputEditText latitudeInput = findViewById(R.id.text_latitude);
                final TextInputEditText longitudeInput = findViewById(R.id.text_longitude);

                containership.setName(Objects.requireNonNull(nameInput.getText()).toString());
                containership.setCaptainName(Objects.requireNonNull(captainInput.getText()).toString());
                containership.setLatitude(Double.parseDouble(Objects.requireNonNull(latitudeInput.getText()).toString()));
                containership.setLongitude(Double.parseDouble(Objects.requireNonNull(longitudeInput.getText()).toString()));
                containership.setType(ContainershipTypeStore.all().get(typeInput.getSelectedItemPosition()));
                containership.setPort(PortStore.all().get(portInput.getSelectedItemPosition()));

                FirebaseFirestore
                    .getInstance()
                    .collection("containerships")
                    .document(containership.getId())
                    .update(containership.getData())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Containership edited!", Toast.LENGTH_SHORT).show();

                        onBackPressed();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to edit containership.", Toast.LENGTH_SHORT).show());

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
