package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.Arrays;
import java.util.List;

import io.github.lingnanlu.gaoxiaolian.ui.fragment.ChatFragment;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.model.User;
import io.github.lingnanlu.gaoxiaolian.R;

public class ChatActivity extends BaseActivity {

    public static final String FROM = "from";
    public static final String CONV_ID = "conversation_id";

    User user;
    ChatFragment fgChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        fgChat = (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.fg_chat);

        Intent intent = getIntent();

        String from = intent.getStringExtra(FROM);

        if (from.equals(UserInfoActivity.class.getSimpleName())) {

           // user = getIntent().getParcelableExtra(UserInfoActivity.USER);

            setTitle(user.getUsername());

            final AVIMClient client = GaoXiaoLian.getClient();
            AVIMConversationQuery conversationQuery = client.getQuery();
            conversationQuery.withMembers(Arrays.asList(user.getObjectId()));
            conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
                @Override
                public void done(List<AVIMConversation> list, AVIMException e) {
                    if (e == null) {
                        Log.d(TAG, "done: conversation query success");
                        if(list != null && list.size() > 0) {
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
                                            if (filter(e)) {
                                                fgChat.setConversation(conversation);
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

        } else {
            String conv_id = intent.getStringExtra(CONV_ID);
            AVIMConversationQuery query = GaoXiaoLian.getClient().getQuery();
            query.whereEqualTo("objectId",conv_id);
            query.findInBackground(new AVIMConversationQueryCallback() {
                @Override
                public void done(List<AVIMConversation> list, AVIMException e) {
                    if (filter(e)) {
                        if (list != null && !list.isEmpty()) {
                            fgChat.setConversation(list.get(0));
                        }
                    }
                }
            });
        }


    }




}
