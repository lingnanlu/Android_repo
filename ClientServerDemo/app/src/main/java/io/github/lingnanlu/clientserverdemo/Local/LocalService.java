package io.github.lingnanlu.clientserverdemo.Local;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Random;

import io.github.lingnanlu.clientserverdemo.PersonNotParcelable;

/**
 * Created by Administrator on 2016/3/26.
 */
public class LocalService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();


    //因为要返回外围类对象的引用, 所以是非静态的
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    //api that the service provide
    public int getRandomNumber() {
        return mGenerator.nextInt();
    }


    // 传递自定义对象没有问题
    public void printPersonInfo(PersonNotParcelable person) {

        //该代码的运行依然在UI线程中, 所以没问题
        Toast.makeText(getApplication(), person.firstname + " " + person.lastname, Toast
                .LENGTH_LONG).show();
    }
}
