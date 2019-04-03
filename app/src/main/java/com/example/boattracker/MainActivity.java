package com.example.boattracker;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.example.boattracker.adapters.ContainershipAdapter;
import com.example.boattracker.models.Container;
import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;
import com.example.boattracker.models.Port;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    List<Containership> containerships;
    List<Port> ports;
    List<ContainershipType> containershipTypes;
    List<Container> containers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.containerships = new ArrayList<>();
        this.ports = new ArrayList<>();
        this.containershipTypes = new ArrayList<>();
        this.containers = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.signInButton).setOnClickListener(v -> {
            Intent sign_in = new Intent(getApplicationContext(), SignInActivity.class);

            startActivity(sign_in);
            finish();
        });

        getData();
    }

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db
            .collection("containerships")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    this.containerships = new ArrayList<>();

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        parseContainership(document);
                    }
                } else {
                    Crashlytics.logException(task.getException());

                    Log.w("FIRESTORE", "Error getting documents.", task.getException());
                }
            });

        /* final ContainershipType type = new ContainershipType("1", "Type", 10, 10, 10);

        final Port port = new Port("1", "Marseille", 43.296482, 5.369780); // Marseille

        final Container container1 = new Container("1", 1, 1, 1);
        final Container container2 = new Container("2", 2, 2, 2);
        final Container container3 = new Container("3", 3, 3, 3);

        final Containership containership1 = new Containership("1", "Le Poséïdon", "Marcel", 43.524910, 5.454140, port, type); // Aix-en-Provence
        final Containership containership2 = new Containership("2", "Le Zeus", "Fred", 0, 0, port, type);
        final Containership containership3 = new Containership("3", "Le Hadès", "Jacques", 0, 0, port, type);
        final Containership containership4 = new Containership("4", "L'Apollon", "Brigitte", 0, 0, port, type);

        containership1.addContainer(container1);
        containership1.addContainer(container2);

        containership1.addContainer(container3);

        this.containerships = new ArrayList<>();

        this.containerships.add(containership1);
        this.containerships.add(containership2);
        this.containerships.add(containership3);
        this.containerships.add(containership4); */
    }

    private void drawUI() {
        final ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainershipAdapter(this, this.containerships));

        shipListView.setOnItemClickListener((parent, view, position, id) -> {
            final Intent intent = new Intent(getApplicationContext(), ContainershipActivity.class);
            intent.putExtra("containership", this.containerships.get(position));
            Bundle bundle = new Bundle();
            bundle.putSerializable("containershipTypes", (Serializable) this.containershipTypes);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
    }

    private void parseContainership(DocumentSnapshot document) {
        Log.d("FIRESTORE", document.getId() + " => " + document.getData());

        final String id = document.getId();

        final String name = document.getString("name");
        final String captain_name = document.getString("captainName");
        final GeoPoint position = document.getGeoPoint("position");

        final DocumentReference port_reference = document.getDocumentReference("port");
        final DocumentReference containership_type_reference = document.getDocumentReference("type");

        Objects.requireNonNull(port_reference)
            .get()
            .addOnCompleteListener(port_task -> {
                if (port_task.isSuccessful()) {
                    final Port port = parsePort(Objects.requireNonNull(port_task.getResult()));

                    Objects.requireNonNull(containership_type_reference)
                        .get()
                        .addOnCompleteListener(containership_type_task -> {
                            if (containership_type_task.isSuccessful()) {
                                final ContainershipType type = parseContainershipType(Objects.requireNonNull(containership_type_task.getResult()));

                                Containership containership = new Containership(
                                    id,
                                    name,
                                    captain_name,
                                    Objects.requireNonNull(position).getLatitude(),
                                    Objects.requireNonNull(position).getLongitude(),
                                    port,
                                    type
                                );

                                this.containerships.add(containership);

                                this.drawUI();
                            } else {
                                Crashlytics.logException(containership_type_task.getException());

                                Log.w("FIRESTORE", "Error getting documents.", containership_type_task.getException());
                            }
                        });
                } else {
                    Crashlytics.logException(port_task.getException());

                    Log.w("FIRESTORE", "Error getting documents.", port_task.getException());
                }
            });
    }

    private Port parsePort(DocumentSnapshot document) {
        Log.d("FIRESTORE", document.getId() + " => " + document.getData());

        final String id = document.getId();

        final String name = document.getString("name");
        final GeoPoint position = document.getGeoPoint("position");

        final Port port = new Port(
            id,
            name,
            Objects.requireNonNull(position).getLatitude(),
            Objects.requireNonNull(position).getLongitude()
        );

        this.ports.add(port);

        return port;
    }

    private ContainershipType parseContainershipType(DocumentSnapshot document) {
        Log.d("FIRESTORE", document.getId() + " => " + document.getData());

        final String id = document.getId();

        final String name = document.getString("name");
        final int length = Objects.requireNonNull(document.getLong("length")).intValue();
        final int height = Objects.requireNonNull(document.getLong("height")).intValue();
        final int width = Objects.requireNonNull(document.getLong("width")).intValue();

        final ContainershipType type = new ContainershipType(id, name, length, height, width);

        this.containershipTypes.add(type);

        return type;
    }
}
