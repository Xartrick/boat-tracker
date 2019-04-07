package com.example.boattracker.store;

import com.example.boattracker.models.Container;
import com.example.boattracker.models.Containership;
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

public class ContainerStore {

    private static List<Container> containers = new ArrayList<>();

    public static Container get(String id) {
        for (Container container : containers) {
            if (container.getId().equals(id)) {
                return container;
            }
        }

        return null;
    }

    public static List<Container> all() {
        return containers;
    }

    public static CompletableFuture<Void> fetch() {
        final CompletableFuture<Void> promise = new CompletableFuture<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db
            .collection(Container.COLLECTION_NAME)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    containers.clear();

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

        final int length = Objects.requireNonNull(document.getLong("length")).intValue();
        final int height = Objects.requireNonNull(document.getLong("height")).intValue();
        final int width = Objects.requireNonNull(document.getLong("width")).intValue();

        final Container container = new Container(id, length, height, width);

        final DocumentReference containership_reference = document.getDocumentReference("containership");

        if (containership_reference != null) {
            final Containership containership = ContainershipStore.get(containership_reference.getId());

            container.setContainership(containership);
        }

        containers.add(container);
    }
}
