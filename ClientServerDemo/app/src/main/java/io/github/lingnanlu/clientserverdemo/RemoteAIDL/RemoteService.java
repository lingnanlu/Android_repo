package io.github.lingnanlu.clientserverdemo.RemoteAIDL;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import io.github.lingnanlu.clientserverdemo.CustomRect;
import io.github.lingnanlu.clientserverdemo.IRemoteService;

public class RemoteService extends Service {
    private static final String TAG = "RemoteService";
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {

        @Override
        public int getPid() throws RemoteException {
            Log.d(TAG, "getPid: ");
            return android.os.Process.myPid();
        }

        @Override
        public int calculatePerimeter(CustomRect rect) throws RemoteException {
            Log.d(TAG, "printRect: ");

            //为什么这里就需要调用Looper.prepare(), 而Messenger那个则不需要
//            Toast.makeText(getApplicationContext(), rect.top + " " + rect.left + " " + rect.right
//                    + " " + rect.bottom, Toast.LENGTH_SHORT).show();
            return rect.top + rect.bottom + rect.right + rect.left;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double
                aDouble, String aString) throws RemoteException {

        }
    };
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
