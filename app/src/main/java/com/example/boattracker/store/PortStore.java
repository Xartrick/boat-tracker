package com.example.boattracker.store;

import com.example.boattracker.models.Port;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class PortStore {

    private static List<Port> ports = new ArrayList<>();

    public static Port get(String id) {
        for (Port port : ports) {
            if (port.getId().equals(id)) {
                return port;
            }
        }

        return null;
    }

    public static List<Port> all() {
        return ports;
    }

    public static CompletableFuture<Void> fetch() {
        final CompletableFuture<Void> promise = new CompletableFuture<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db
            .collection(Port.COLLECTION_NAME)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ports.clear();

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        parse(document);
                    }

                    promise.complete(null);
                } else {
                    promise.completeExceptionally(task.getException());
                }
            })
            .addOnFailureListener(promise::completeExceptionally);

        return promise;
    }

    private static void parse(DocumentSnapshot document) {
        final String id = document.getId();

        final String name = document.getString("name");
        final GeoPoint position = document.getGeoPoint("position");

        final Port port = new Port(
            id,
            name,
            Objects.requireNonNull(position).getLatitude(),
            Objects.requireNonNull(position).getLongitude()
        );

        ports.add(port);
    }
}
