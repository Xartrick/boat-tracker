package com.example.boattracker.documents;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public interface IDocument {

    void put(String key, Object value);
    Object get(String key);

    String getDocumentPath();

    /**
     * Get Firebase document reference
     *
     * @return Document reference
     */
    default DocumentReference getDocumentReference() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.document(this.getDocumentPath());
    }
}
