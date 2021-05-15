package com.benew.marryme.Controllers.Fragments;

import com.benew.marryme.Bases.BaseFragment;
import com.benew.marryme.R;

public class TchatFragment extends BaseFragment {

    public TchatFragment() {}

    public static TchatFragment newInstance() { return new TchatFragment(); }

    @Override
    public int getLayout() { return R.layout.fragment_tchat; }

    @Override
    public void configuration() {

    }
}