package com.auth.provider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceBroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = "ServiceBC";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "get action : " + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Log.d(TAG, "Start DataAccessService!");
            context.startService(new Intent(context, DataAccessService.class));
        }

    }
}
