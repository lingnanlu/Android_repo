package io.github.lingnanlu.gaoxiaolian;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by rico on 4/25/2016.
 */
public class GaoXiaoLian extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        AVOSCloud.initialize(this, "YldGMeRURSIPLfupg3omoGwD-gzGzoHsz", "fArCa58wcKw5bsLRCmGY4nWq");

    }
}
