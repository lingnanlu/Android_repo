package io.github.lingnanlu.gaoxiaolian.event;

/**
 * Created by Administrator on 2016/4/28.
 */
public class InputBottomBarTextEvent extends InputBottomBarEvent {

    public String content;

    public InputBottomBarTextEvent(int action, String content,  Object tag) {
        super(action, tag);

        this.content = content;
    }
}
