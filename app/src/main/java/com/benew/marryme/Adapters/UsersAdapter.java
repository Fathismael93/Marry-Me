package com.benew.marryme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.benew.marryme.FirebaseUsage.FirestoreUsage;
import com.benew.marryme.Holders.UsersViewHolder;
import com.benew.marryme.Modals.User;
import com.benew.marryme.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UsersAdapter extends FirestoreRecyclerAdapter<User, UsersViewHolder> {

    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param context
     */
    public UsersAdapter(@NonNull FirestoreRecyclerOptions<User> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int i, @NonNull User user) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(user.getProfile_picture());
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(context).load(uri)
                        .into(usersViewHolder.image));

        
        usersViewHolder.name.setText(user.getName());
        usersViewHolder.city.setText(user.getCity());
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spot, parent, false);

        return new UsersViewHolder(view);
    }
}
