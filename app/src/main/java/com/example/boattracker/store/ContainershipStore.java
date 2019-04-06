package com.example.boattracker.store;

import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;
import com.example.boattracker.models.Port;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ContainershipStore {

    private static List<Containership> containerships = new ArrayList<>();

    public static Containership get(String id) {
        for (Containership containership : containerships) {
            if (containership.getId().equals(id)) {
                return containership;
            }
        }

        return null;
    }

    public static List<Containership> all() {
        return containerships;
    }

    public static CompletableFuture<Void> fetch() {
        final CompletableFuture<Void> promise = new CompletableFuture<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db
                .collection(Containership.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        containerships.clear();

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
        final String captain_name = document.getString("captainName");
        final GeoPoint position = document.getGeoPoint("position");

        final DocumentReference port_reference = document.getDocumentReference("port");
        final Port port = PortStore.get(Objects.requireNonNull(port_reference).getId());

        final DocumentReference containership_type_reference = document.getDocumentReference("type");
        final ContainershipType type = ContainershipTypeStore.get(Objects.requireNonNull(containership_type_reference).getId());

        Containership containership = new Containership(
            id,
            name,
            captain_name,
            Objects.requireNonNull(position).getLatitude(),
            Objects.requireNonNull(position).getLongitude(),
            port,
            type
        );

        containerships.add(containership);
    }
}
