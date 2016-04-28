package io.github.lingnanlu.gaoxiaolian;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import io.github.lingnanlu.gaoxiaolian.Activity.HomeActivity;

/**
 * Created by rico on 4/25/2016.
 */
public class GaoXiaoLian extends Application {

    static AVUser user;
    static AVIMClient client;

    @Override
    public void onCreate() {

        super.onCreate();
        AVOSCloud.initialize(this, "YldGMeRURSIPLfupg3omoGwD-gzGzoHsz", "fArCa58wcKw5bsLRCmGY4nWq");

    }

    public static AVUser getUser() {
        return user;
    }

    public static void setUser(AVUser user) {
        GaoXiaoLian.user = user;
    }

    public static AVIMClient getClient() {
        return client;
    }

    public static void setClient(AVIMClient client) {
       GaoXiaoLian.client = client;
    }

    public static void login(String name, String password) {}
}
