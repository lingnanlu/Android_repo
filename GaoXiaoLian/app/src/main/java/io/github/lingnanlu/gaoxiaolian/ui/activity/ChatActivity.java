package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.List;

import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.ConversationHelper;
import io.github.lingnanlu.gaoxiaolian.ui.fragment.ChatFragment;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.model.User;
import io.github.lingnanlu.gaoxiaolian.R;

public class ChatActivity extends BaseActivity {

    public static final String FROM = "from";
    public static final String USERNAME = "username";
    public static final String CONV_ID = "conversation_id";

    ChatFragment fgChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        fgChat = (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.fg_chat);

        Intent intent = getIntent();

        String from = intent.getStringExtra(FROM);

        String username = intent.getStringExtra(USERNAME);
        if (from.equals(UserInfoActivity.class.getSimpleName())) {

            setTitle(username);

            ConversationHelper.getConversationByMember(username, new CallBack<AVIMConversation>() {
                @Override
                public void onResult(AVIMConversation result) {
                    fgChat.setConversation(result);
                }

                @Override
                public void onError(AVException e) {

                }
            });

        } else {
            String conv_id = intent.getStringExtra(CONV_ID);
            ConversationHelper.getConversationByID(conv_id, new CallBack<AVIMConversation>() {
                @Override
                public void onResult(AVIMConversation result) {
                    fgChat.setConversation(result);
                }

                @Override
                public void onError(AVException e) {

                }
            });
        }


    }




}
