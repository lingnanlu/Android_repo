package io.github.lingnanlu.launchmodedemo;

import android.util.Log;

public class StandardActivity extends BaseActivity {

    private static final String TAG = "StandardActivity";
    public static int count = 0;
    public final int id = count++;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: id " + id + " Task ID is " + getTaskId());
    }


}
