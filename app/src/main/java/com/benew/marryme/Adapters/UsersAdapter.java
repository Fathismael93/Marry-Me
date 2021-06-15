package com.benew.marryme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.benew.marryme.Holders.UsersViewHolder;
import com.benew.marryme.Modals.User;
import com.benew.marryme.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getFemaleUserDocumentReference;
import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getMaleUserDocumentReference;
import static com.benew.marryme.UTILS.Constants.MALE_GENDER;

public class UsersAdapter extends FirestoreRecyclerAdapter<User, UsersViewHolder> {

    private static User user;
    User currentUserOnline;
    Context context;
    private static Snackbar snackbar;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param context
     */
    public UsersAdapter(@NonNull FirestoreRecyclerOptions<User> options, Context context, User currentUserOnline, Snackbar snackbar) {
        super(options);
        this.context = context;
        this.currentUserOnline = currentUserOnline;
        UsersAdapter.snackbar = snackbar;
    }

    @Override
    protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int i, @NonNull User user) {

        UsersAdapter.user = user;

        if (currentUserOnline.getGender().equals(MALE_GENDER))
            getMaleUserDocumentReference(currentUserOnline.getMail()).collection("MATCHES").document(user.getMail()).get().addOnCompleteListener(task -> {
                DocumentSnapshot documentSnapshot = task.getResult();

                if (!documentSnapshot.exists()) {
                    getMaleUserDocumentReference(currentUserOnline.getMail()).collection("LIKES").document(user.getMail()).get().addOnCompleteListener(task1 -> {
                        DocumentSnapshot documentSnapshot1 = task1.getResult();

                        if (!documentSnapshot1.exists())
                            usersViewHolder.completeCardWithData(user);
                    });
                }
            });
        else
            getFemaleUserDocumentReference(currentUserOnline.getMail()).collection("MATCHES").document(user.getMail()).get().addOnCompleteListener(task -> {
                DocumentSnapshot documentSnapshot = task.getResult();

                if (!documentSnapshot.exists()) {
                    getFemaleUserDocumentReference(currentUserOnline.getMail()).collection("LIKES").document(user.getMail()).get().addOnCompleteListener(task1 -> {
                        DocumentSnapshot documentSnapshot1 = task1.getResult();

                        if (!documentSnapshot1.exists())
                            usersViewHolder.completeCardWithData(user);
                    });
                }
            });
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spot, parent, false);

        return new UsersViewHolder(view, context);
    }


    public static void registerLikeFromAdapater(User currentUserOnline) {
        if (user.getGender().equals(MALE_GENDER))
            getMaleUserDocumentReference(user.getMail()).collection("LIKES").document(currentUserOnline.getMail()).get().addOnCompleteListener(task -> {
                DocumentSnapshot documentSnapshot = task.getResult();

                if (documentSnapshot.exists()) {
                    registerMatches(currentUserOnline, snackbar);
                    deleteFromLikes(currentUserOnline);
                } else
                    registerLikes(currentUserOnline);
            });
        else
            getFemaleUserDocumentReference(user.getMail()).collection("LIKES").document(currentUserOnline.getMail()).get().addOnCompleteListener(task -> {
                DocumentSnapshot documentSnapshot = task.getResult();

                if (documentSnapshot.exists()) {
                    registerMatches(currentUserOnline, snackbar);
                    deleteFromLikes(currentUserOnline);
                } else
                    registerLikes(currentUserOnline);
            });
    }


    private static void registerLikes(User currentUserOnline) {
        if (currentUserOnline.getGender().equals(MALE_GENDER))
            getMaleUserDocumentReference(currentUserOnline.getMail()).collection("LIKES").document(user.getMail()).set(userSwipedMap(user));
        else
            getFemaleUserDocumentReference(currentUserOnline.getMail()).collection("LIKES").document(user.getMail()).set(userSwipedMap(user));
    }


    private static void registerMatches(User currentUserOnline, Snackbar snackbar) {
        if (currentUserOnline.getGender().equals(MALE_GENDER)) {

            getMaleUserDocumentReference(currentUserOnline.getMail()).collection("MATCHES").document(user.getMail()).set(userSwipedMap(user)).addOnSuccessListener(aVoid -> {
                snackbar.show();
                getFemaleUserDocumentReference(user.getMail()).collection("MATCHES").document(currentUserOnline.getMail()).set(userSwipedMap(currentUserOnline));
            });

        } else {
            getFemaleUserDocumentReference(currentUserOnline.getMail()).collection("MATCHES").document(user.getMail()).set(userSwipedMap(user)).addOnSuccessListener(aVoid -> {
                snackbar.show();
                getMaleUserDocumentReference(user.getMail()).collection("MATCHES").document(currentUserOnline.getMail()).set(userSwipedMap(currentUserOnline));
            });
        }
    }


    private static void deleteFromLikes(User currentUserOnline) {
        if (user.getGender().equals(MALE_GENDER))
            getMaleUserDocumentReference(user.getMail()).collection("LIKES").document(currentUserOnline.getMail()).delete();
        else
            getFemaleUserDocumentReference(user.getMail()).collection("LIKES").document(currentUserOnline.getMail()).delete();
    }


    public static void registerSwipeFromAdapater(User currentUserOnline) {

        if (currentUserOnline.getGender().equals(MALE_GENDER))
            getMaleUserDocumentReference(currentUserOnline.getMail()).collection("PASS").document(user.getMail()).set(userSwipedMap(user));
        else
            getFemaleUserDocumentReference(currentUserOnline.getMail()).collection("PASS").document(user.getMail()).set(userSwipedMap(user));
    }


    private static Map userSwipedMap(User user) {
        Map userSwipedMap = new HashMap();
        userSwipedMap.put("mail", user.getMail());
        userSwipedMap.put("userID", user.getUserID());

        return userSwipedMap;
    }
}
