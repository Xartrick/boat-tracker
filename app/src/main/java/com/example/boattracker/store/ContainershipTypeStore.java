package com.example.boattracker.store;

import com.example.boattracker.models.ContainershipType;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ContainershipTypeStore {

    private static List<ContainershipType> containershipTypes = new ArrayList<>();

    public static ContainershipType get(String id) {
        for (ContainershipType type : containershipTypes) {
            if (type.getId().equals(id)) {
                return type;
            }
        }

        return null;
    }

    public static List<ContainershipType> all() {
        return containershipTypes;
    }

    public static CompletableFuture<Void> fetch() {
        final CompletableFuture<Void> promise = new CompletableFuture<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db
            .collection(ContainershipType.COLLECTION_NAME)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    containershipTypes.clear();

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
        final int length = Objects.requireNonNull(document.getLong("length")).intValue();
        final int height = Objects.requireNonNull(document.getLong("height")).intValue();
        final int width = Objects.requireNonNull(document.getLong("width")).intValue();

        final ContainershipType type = new ContainershipType(id, name, length, height, width);

        containershipTypes.add(type);
    }
}
