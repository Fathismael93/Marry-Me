package com.benew.marryme.FirebaseUsage;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.benew.marryme.UTILS.Constants.USERS_COLLECTION;

public class FirestoreUsage {

    // ALL USERS COLLECTION REFERENCE
    public static CollectionReference getUsersCollectionReference() {
        return FirebaseFirestore.getInstance().collection(USERS_COLLECTION);
    }

    // ONE USER DOCUMENT REFERENCE
    public static DocumentReference getUserDocumentReference(String userNumber) {
        return getUsersCollectionReference().document(userNumber);
    }
}
