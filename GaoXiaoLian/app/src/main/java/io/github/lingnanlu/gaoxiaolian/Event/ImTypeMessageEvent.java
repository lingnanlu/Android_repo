package io.github.lingnanlu.gaoxiaolian.Event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by Administrator on 2016/4/28.
 */
public class ImTypeMessageEvent  {

    public AVIMTypedMessage message;
    public AVIMConversation conversation;
}
