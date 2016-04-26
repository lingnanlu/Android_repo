package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.Arrays;

import io.github.lingnanlu.gaoxiaolian.R;

public class ConversationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Log.d(TAG, "onCreate: " + getIntent().getStringExtra("username"));

        AVIMClient rabbit = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());

        rabbit.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: client open success");
                    client.createConversation(Arrays.asList("Xiaolu"), "Rabbit & XiaoLu", null,
                            new AVIMConversationCreatedCallback() {
                        @Override
                        public void done(AVIMConversation conversation, AVIMException e) {
                            if (e == null) {
                                Log.d(TAG, "done: conversation created success");
                                AVIMTextMessage msg = new AVIMTextMessage();
                                msg.setText("ni hao a");
                                conversation.sendMessage(msg, new AVIMConversationCallback() {
                                    @Override
                                    public void done(AVIMException e) {
                                        if (e == null) {
                                            Log.d(TAG, "done: send success");
                                        } else {
                                            Log.d(TAG, "done: send failed");
                                        }
                                    }
                                });
                            } else {
                                Log.d(TAG, "done: conversation created failed");
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "done: client open success failed");
                }

            }
        });
    }


}
