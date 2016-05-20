package io.github.lingnanlu.pushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/5/20.
 */
public class CustomReceiver extends BroadcastReceiver {

    private static final String TAG = "CustomReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        org.json.JSONObject json = null;
        try {
            json = new org.json.JSONObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
            final String message = json.getString("alert");
            Log.d(TAG, "onReceive: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}