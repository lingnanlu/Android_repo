package io.github.lingnanlu.clientserverdemo.Local;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.github.lingnanlu.clientserverdemo.PersonNotParcelable;
import io.github.lingnanlu.clientserverdemo.R;

/*
 * 该Activity测试本地Service
 */
public class BinderActivity extends AppCompatActivity {

    private static final String TAG = "BinderActivity";
    LocalService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, LocalService.class), new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
                mService = binder.getService();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

        }, Context.BIND_AUTO_CREATE);
    }

    public void onButtonClick(View v) {
        Log.d(TAG, "Random Number " + mService.getRandomNumber());
        mService.printPersonInfo(new PersonNotParcelable("kobe", "bryant"));
    }
}
