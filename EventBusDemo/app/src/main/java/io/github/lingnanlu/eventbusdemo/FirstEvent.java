package io.github.lingnanlu.eventbusdemo;

/**
 * Created by Administrator on 2016/4/7.
 */
public class FirstEvent {

    private String mMsg;

    public FirstEvent(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
