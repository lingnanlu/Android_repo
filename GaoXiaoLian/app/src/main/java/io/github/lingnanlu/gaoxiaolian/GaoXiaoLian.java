package io.github.lingnanlu.gaoxiaolian;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

import java.util.HashMap;
import java.util.Map;

import io.github.lingnanlu.gaoxiaolian.model.Like;
import io.github.lingnanlu.gaoxiaolian.model.Online;
import io.github.lingnanlu.gaoxiaolian.model.User;
import io.github.lingnanlu.gaoxiaolian.model.Visit;

/**
 * Created by rico on 4/25/2016.
 */
public class GaoXiaoLian extends Application {

    static User user;
    static AVIMClient client;

    public static Map<Integer, String> user_attrs;
    @Override
    public void onCreate() {

        super.onCreate();

        AVUser.alwaysUseSubUserClass(User.class);
        AVObject.registerSubclass(Online.class);
        AVObject.registerSubclass(Like.class);
        AVObject.registerSubclass(Visit.class);

        AVOSCloud.initialize(this, "YldGMeRURSIPLfupg3omoGwD-gzGzoHsz", "fArCa58wcKw5bsLRCmGY4nWq");

        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

        user_attrs = new HashMap<>();

        user_attrs.put(R.string.college, getResources().getString(R.string.college));

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
