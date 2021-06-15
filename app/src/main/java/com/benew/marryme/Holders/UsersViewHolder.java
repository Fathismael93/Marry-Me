package com.benew.marryme.Holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benew.marryme.Modals.User;
import com.benew.marryme.R;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    Context context;

    @BindView(R.id.item_name) public TextView name;
    @BindView(R.id.item_city) public TextView city;
    @BindView(R.id.item_image) public ImageView image;

    public UsersViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void completeCardWithData(User user) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(user.getProfile_picture());
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(context).load(uri)
                .into(image));


        name.setText(user.getName());
        city.setText(user.getCity());
    }
}
