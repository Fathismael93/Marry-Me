package com.benew.marryme.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benew.marryme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_name) public TextView name;
    @BindView(R.id.item_city) public TextView city;
    @BindView(R.id.item_image) public ImageView image;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
