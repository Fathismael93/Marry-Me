package com.benew.marryme.API;

import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.core.content.ContextCompat;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.benew.marryme.R;

public class LoadingConfiguration {

    public static TashieLoader configureLoading(Context context) {
        TashieLoader tashie = new TashieLoader(
                context, 5,
                30, 10,
                ContextCompat.getColor(context, R.color.purple_200));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());
        tashie.setY(300f);
        tashie.setX(180f);

        return tashie;
    }

    public static void startLoading(ViewGroup rootView, TashieLoader tashie) {
        rootView.setAlpha(0.3f);
        rootView.addView(tashie);
    }

    public static void stopLoading(ViewGroup rootView, TashieLoader tashie) {
        rootView.setAlpha(1f);
        rootView.removeView(tashie);
    }
}
