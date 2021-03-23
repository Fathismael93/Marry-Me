package com.benew.marryme.Bases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public abstract int getLayout();
    public abstract void configuration();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);

        configuration();

        return view;
    }
}
