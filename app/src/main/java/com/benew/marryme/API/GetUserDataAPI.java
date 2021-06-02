package com.benew.marryme.API;

import android.app.Activity;
import android.content.Context;

import com.benew.marryme.Controllers.Activities.MainActivity;
import com.benew.marryme.Controllers.Activities.NoyauActivity;
import com.benew.marryme.FirebaseUsage.FirestoreUsage;
import com.benew.marryme.Modals.User;
import com.benew.marryme.UTILS.Prevalent;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import static com.benew.marryme.API.StartNewActivityAPI.startNewActivity;

public class GetUserDataAPI {

    public static void getUserData(Context context, Activity activity, FirebaseUser user) {
        FirestoreUsage.getFemaleUserDocumentReference(user.getEmail()).get().addOnCompleteListener(task -> {

            DocumentSnapshot documentSnapshot = task.getResult();

            if (documentSnapshot.exists()) {
                User user1 = documentSnapshot.toObject(User.class);
                Prevalent.currentUserOnline = user1;
                startNewActivity(context, NoyauActivity.class);
                activity.finish();
            } else {

                FirestoreUsage.getMaleUserDocumentReference(user.getEmail()).get().addOnCompleteListener(task1 -> {

                    DocumentSnapshot document = task1.getResult();

                    if (document.exists()) {
                        User user2 = document.toObject(User.class);
                        Prevalent.currentUserOnline = user2;
                        startNewActivity(context, NoyauActivity.class);
                        activity.finish();
                    }
                });
            }
        });
    }
}
