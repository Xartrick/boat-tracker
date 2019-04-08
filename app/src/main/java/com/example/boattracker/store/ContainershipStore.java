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

    private final static List<Containership> containerships = new ArrayList<>();

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

        FirebaseFirestore
            .getInstance()
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

    public static CompletableFuture<Void> fetch(String id) {

        final CompletableFuture<Void> promise = new CompletableFuture<>();

        FirebaseFirestore
            .getInstance()
            .collection(Containership.COLLECTION_NAME)
            .document(id)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document == null) {
                        promise.completeExceptionally(new RuntimeException("Document " + id + " is null"));

                        return;
                    }

                    parse(document);

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
        final String captainName = document.getString("captainName");
        final GeoPoint position = document.getGeoPoint("position");

        final DocumentReference portReference = document.getDocumentReference("port");
        final Port port = PortStore.get(Objects.requireNonNull(portReference).getId());

        final DocumentReference typeReference = document.getDocumentReference("type");
        final ContainershipType type = ContainershipTypeStore.get(Objects.requireNonNull(typeReference).getId());

        final Containership containership = new Containership(
            id,
            name,
            captainName,
            Objects.requireNonNull(position).getLatitude(),
            Objects.requireNonNull(position).getLongitude(),
            port,
            type
        );

        final Containership old = get(id);
        if (old != null) {
            old.replace(containership);

            return;
        }

        containerships.add(containership);
    }
}
