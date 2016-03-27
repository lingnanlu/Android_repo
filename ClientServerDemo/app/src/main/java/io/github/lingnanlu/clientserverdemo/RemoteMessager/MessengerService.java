package io.github.lingnanlu.clientserverdemo.RemoteMessager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import io.github.lingnanlu.clientserverdemo.Person;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    static final int MSG_SAY_HELLO = 1;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_SAY_HELLO:

                    Bundle bundle = msg.getData();

                    //bundle.setClassLoader(getClass().getClassLoader());

                    Log.i(TAG, "handleMessage: " + bundle.getClassLoader());
                    Person person = bundle.getParcelable("Test");
                    Log.d(TAG, "handleMessage " + msg.what + person.toString());
                    Toast.makeText(getApplication(), person.toString(), Toast.LENGTH_LONG).show();
                    String packageName = getApplication().getPackageName();

                    //这里可以直接在另一个进程中显示Toast, 但在AIDL方式中却不可以, 为什么?
                    Toast.makeText(getApplication(), "Hello!" + Thread.currentThread() +
                            android.os.Process.myPid() + packageName, Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {

        Toast.makeText(getApplication(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }
}

