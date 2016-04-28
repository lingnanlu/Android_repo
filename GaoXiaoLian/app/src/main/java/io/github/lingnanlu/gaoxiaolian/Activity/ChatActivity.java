package io.github.lingnanlu.gaoxiaolian.Activity;

import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;


import java.util.Arrays;
import java.util.List;

import io.github.lingnanlu.gaoxiaolian.Fragment.ChatFragment;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;

public class ChatActivity extends BaseActivity {

    AVUser user;
    ChatFragment fgChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        fgChat = (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.fg_chat);
        user = getIntent().getParcelableExtra("user");

        setTitle(user.getUsername());


        final AVIMClient client = GaoXiaoLian.getClient();
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.withMembers(Arrays.asList(user.getObjectId()));
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: conversation query success");
                    if(list != null) {
                        fgChat.setConversation(list.get(0));
                    } else {
                        //创建对话
                        client.createConversation(
                                Arrays.asList(user.getObjectId()),
                                null,
                                null,
                                false,
                                true,
                                new AVIMConversationCreatedCallback() {
                                    @Override
                                    public void done(AVIMConversation conversation, AVIMException e) {
                                        if (e == null) {
                                            Log.d(TAG, "done: coversation created success");
                                            fgChat.setConversation(conversation);
                                        } else {
                                            Log.d(TAG, "done: coversation created failed");
                                        }

                                    }
                                }
                        );
                    }
                } else {
                    Log.d(TAG, "done: conversation query failed");
                }
            }
        });

    }


}
