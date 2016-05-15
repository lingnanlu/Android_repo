package io.github.lingnanlu.gaoxiaolian.event;

/**
 * Created by Administrator on 2016/4/28.
 */
public class InputBottomBarEvent {

    public static final int INPUTBOTTOMBAR_SEND_TEXT_ACTION = 0;

    public int eventAction;
    public Object tag;

    public InputBottomBarEvent(int eventAction, Object tag) {
        this.eventAction = eventAction;
        this.tag = tag;
    }
}
