package com.waf.soma.wearefamily;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {

            Log.i("Broadcast", "Lockscreen!");

            abortBroadcast();


        }
    }
}
