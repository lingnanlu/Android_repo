package io.github.lingnanlu.clientserverdemo.RemoteAIDL;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import io.github.lingnanlu.clientserverdemo.CustomRect;
import io.github.lingnanlu.clientserverdemo.IRemoteService;
import io.github.lingnanlu.clientserverdemo.R;

/*
 * 利用AIDL进行RemoteService通信
 *
 */
public class AIDLActivity extends AppCompatActivity {

    private static final String TAG = "AIDLActivity";
    IRemoteService mIRemoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);


    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, RemoteService.class), new ServiceConnection() {
            
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: ");
                mIRemoteService = IRemoteService.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

        }, Context.BIND_AUTO_CREATE);
    }

    public void aidlService(View v) {
        Log.d(TAG, "aidlService: ");
        try {
            CustomRect customRect = new CustomRect();
            customRect.top = 5;
            customRect.right = 6;
            customRect.left = 7;
            customRect.bottom = 8;

            Toast.makeText(getApplication(), android.os.Process.myPid() + "", Toast.LENGTH_SHORT)
                    .show();
            Toast.makeText(getApplication(), mIRemoteService.calculatePerimeter(customRect) + "", Toast
                    .LENGTH_SHORT).show();
            Toast.makeText(getApplication(), mIRemoteService.getPid() + "", Toast.LENGTH_SHORT)
                    .show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
