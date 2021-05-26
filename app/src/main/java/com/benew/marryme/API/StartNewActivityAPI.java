package com.benew.marryme.API;

import android.content.Context;
import android.content.Intent;

public class StartNewActivityAPI {

    public static void startNewActivity(Context actualActivity, Class newActivity) {
        Intent intent = new Intent(actualActivity, newActivity);
        actualActivity.startActivity(intent);
    }
}
