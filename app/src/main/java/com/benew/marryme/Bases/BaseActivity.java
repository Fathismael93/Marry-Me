package com.benew.marryme.Bases;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.benew.marryme.Receivers.ConnectivityReceiver;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    protected abstract int getLayout();
    protected abstract void configuration();

    private ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        configuration();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver,filter);

        ConnectivityReceiver.setConnectivityListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectivityReceiver);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {}
}
