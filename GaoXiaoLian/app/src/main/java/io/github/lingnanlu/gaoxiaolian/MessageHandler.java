package io.github.lingnanlu.gaoxiaolian;

import android.content.Context;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;

import de.greenrobot.event.EventBus;
import io.github.lingnanlu.gaoxiaolian.Event.ImTypeMessageEvent;

/**
 * Created by Administrator on 2016/4/28.
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage>{


    private Context context;

    public MessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {

        String clientID = "";

        clientID = GaoXiaoLian.getClient().getClientId();
        if (client.getClientId().equals(clientID)) {

            if (!message.getFrom().equals(clientID)) {
                ImTypeMessageEvent event = new ImTypeMessageEvent();
                event.message = message;
                event.conversation = conversation;
                EventBus.getDefault().post(event);
            }
        }
    }
}
