package com.asus.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    public static final String INTENT_UPDATE_SHORTCUT = "com.htc.launcher.action.UPDATE_SHORTCUT";
    public static final String INTENT_SET_NOTIFICATION = "com.htc.launcher.action.SET_NOTIFICATION";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("fight", intent.getAction());
    }
}
