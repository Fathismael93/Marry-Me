package com.benew.marryme.FirebaseUsage;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.benew.marryme.UTILS.Constants.FEMALE_USERS_COLLECTION;
import static com.benew.marryme.UTILS.Constants.MALE_USERS_COLLECTION;

public class FirestoreUsage {

    // ALL USERS COLLECTION REFERENCE
    public static CollectionReference getFemaleUsersCollectionReference() {
        return FirebaseFirestore.getInstance().collection(FEMALE_USERS_COLLECTION);
    }

    public static CollectionReference getMaleUsersCollectionReference() {
        return FirebaseFirestore.getInstance().collection(MALE_USERS_COLLECTION);
    }

    // ONE USER DOCUMENT REFERENCE
    public static DocumentReference getFemaleUserDocumentReference(String userMail) {
        return getFemaleUsersCollectionReference().document(userMail);
    }

    public static DocumentReference getMaleUserDocumentReference(String userMail) {
        return getMaleUsersCollectionReference().document(userMail);
    }

    // ALL USERS STORAGE REFERENCE
    public static StorageReference getAllUsersStorageRef() {
        return FirebaseStorage.getInstance().getReference().child("USERS_COLLECTION");
    }

    // ONE USER STORAGE REFERENCE
    public static StorageReference getUserPictureReference(String userNumber) {
        return getAllUsersStorageRef().child(userNumber).child("PROFILE PICTURE");
    }
}
