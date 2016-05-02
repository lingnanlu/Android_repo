package io.github.lingnanlu.gaoxiaolian;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by rico on 4/25/2016.
 */
public class GaoXiaoLian extends Application {

    static User user;
    static AVIMClient client;

    @Override
    public void onCreate() {

        super.onCreate();

        AVUser.alwaysUseSubUserClass(User.class);
        AVOSCloud.initialize(this, "YldGMeRURSIPLfupg3omoGwD-gzGzoHsz", "fArCa58wcKw5bsLRCmGY4nWq");

        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));


    }


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
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
