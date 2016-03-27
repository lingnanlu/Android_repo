package io.github.lingnanlu.clientserverdemo.RemoteMessager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.github.lingnanlu.clientserverdemo.Person;
import io.github.lingnanlu.clientserverdemo.R;

/*
 * 该Activtiy测试使用Messenger与RemoteService进行通信, 包括简单数据与Parceble数据
 */
public class ActivityMessenger extends AppCompatActivity {

    private static final String TAG = "ActivityMessenger";
    Messenger mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_messenger);

//        String packageName = getApplication().getPackageName();
//        Toast.makeText(this, Thread.currentThread() + " " + android.os.Process.myPid() + packageName, Toast.LENGTH_LONG).
//                show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, MessengerService.class), new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

        }, Context.BIND_AUTO_CREATE);
    }

    public void sayHello(View v) {
        Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
        try{
            Bundle bundle = new Bundle();
            Log.i(TAG, "sayHello: Message" + Message.class.getClassLoader());
            Log.i(TAG, "sayHello: Bundle" + Bundle.class.getClassLoader());
            Log.i(TAG, "sayHello: " + bundle.getClassLoader());
            bundle.putParcelable("Test", new Person("kobe", "bryant"));
            msg.setData(bundle);
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
