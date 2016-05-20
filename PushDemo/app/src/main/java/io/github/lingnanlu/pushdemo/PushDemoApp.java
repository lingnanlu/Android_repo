package io.github.lingnanlu.pushdemo;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2016/5/20.
 */
public class PushDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

       // AVOSCloud.setLogLevel(AVOSCloud.LOG_LEVEL_VERBOSE);
        AVOSCloud.setDebugLogEnabled(true);
        AVOSCloud.initialize(this, "YldGMeRURSIPLfupg3omoGwD-gzGzoHsz", "fArCa58wcKw5bsLRCmGY4nWq");
    }
}
