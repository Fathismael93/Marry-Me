package com.benew.marryme.FirebaseUsage;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    // ALL USERS STORAGE REFERENCE
    public static StorageReference getAllUsersStorageRef() {
        return FirebaseStorage.getInstance().getReference().child(USERS_COLLECTION);
    }

    // ONE USER STORAGE REFERENCE
    public static StorageReference getUserPictureReference(String userNumber) {
        return getAllUsersStorageRef().child(userNumber).child("PROFILE PICTURE");
    }
}
