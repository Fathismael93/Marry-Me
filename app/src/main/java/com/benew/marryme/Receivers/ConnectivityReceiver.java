package com.benew.marryme.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener receiverListener;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = isConnected(context);

        if (receiverListener != null)
            receiverListener.onNetworkConnectionChanged(isConnected);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener receiverListener) {
        ConnectivityReceiver.receiverListener = receiverListener;
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged (boolean isConnected);
    }
}
