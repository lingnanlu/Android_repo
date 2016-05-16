package io.github.lingnanlu.gaoxiaolian.core.helper;

import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;

/**
 * Created by rico on 5/16/2016.
 */
public class ConversationHelper {

    private static final String TAG = "ConversationHelper";

    public static void getConversations(final CallBack<List<AVIMConversation>> cb) {

        AVIMConversationQuery query = ClientHelper.getInstance().getClient().getQuery();

        query.containsMembers(Arrays.asList(GaoXiaoLian.getUser().getUsername()));
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> conversations, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: get conversions success" + conversations);
                    cb.onResult(conversations);
                } else {
                    Log.d(TAG, "done: get conversions failed " + e);
                    cb.onError(e);
                }
            }
        });

    }

    //获得当前用户和username用户的对话.
    public static void getConversationByMember(final String username, final CallBack<AVIMConversation> cb) {

        AVIMConversationQuery query = ClientHelper.getInstance().getClient().getQuery();

        query.containsMembers(Arrays.asList(GaoXiaoLian.getUser().getUsername(), username));
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: get conversation success");
                    if (list != null && list.size() > 0) {
                        cb.onResult(list.get(0));
                    } else {
                        //创建对话
                        ClientHelper.getInstance().getClient().createConversation(
                                Arrays.asList(GaoXiaoLian.getUser().getUsername(), username),
                                null,
                                null,
                                false,
                                true,
                                new AVIMConversationCreatedCallback() {
                                    @Override
                                    public void done(AVIMConversation conversation, AVIMException e) {
                                        if (e == null) {
                                            Log.d(TAG, "done: create conversation success");
                                            cb.onResult(conversation);
                                        } else {
                                            Log.d(TAG, "done: create conversation failed " + e);
                                            cb.onError(e);
                                        }
                                    }
                                }
                        );
                    }
                } else {
                    Log.d(TAG, "done: get conversation failed " + e);
                }
            }
        });
    }

    public static void getConversationByID(String conv_id, final CallBack<AVIMConversation> cb) {

        AVIMConversationQuery query = ClientHelper.getInstance().getClient().getQuery();

        query.whereEqualTo("objectId", conv_id);
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: get conversation by id success");
                    cb.onResult(list.get(0));
                } else {
                    Log.d(TAG, "done: get conversation by id failed " + e);
                    cb.onError(e);
                }

            }
        });
    }

    public static void getLastMessage(AVIMConversation conversation, final CallBack<AVIMMessage> cb) {
        if (conversation != null) {
            conversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
                @Override
                public void done(AVIMMessage avimMessage, AVIMException e) {
                    if (e == null) {
                        Log.d(TAG, "done: get last message success");
                        cb.onResult(avimMessage);
                    } else {
                        Log.d(TAG, "done: get last message failed" + e);
                        cb.onError(e);
                    }
                }
            });
        }
    }

    public static void queryMessages(AVIMConversation conversation, final CallBack<List<AVIMMessage>> cb) {

        if (conversation != null) {
            conversation.queryMessages(new AVIMMessagesQueryCallback() {
                @Override
                public void done(List<AVIMMessage> list, AVIMException e) {
                    if (e == null) {
                        Log.d(TAG, "done: message fetch success");
                        cb.onResult(list);
                    } else {
                        Log.d(TAG, "done: message fetch failed " + e);
                        cb.onError(e);
                    }
                }
            });
        }
    }

    public static void sendMessage(AVIMConversation conversation, AVIMTextMessage message, final CallBack<Void> cb) {
        if (conversation != null && message != null) {

            conversation.sendMessage(message, new AVIMConversationCallback() {
                @Override
                public void done(AVIMException e) {
                    if (e == null) {
                        Log.d(TAG, "done: send message success");
                        cb.onResult(null);
                    } else {
                        Log.d(TAG, "done: send message failed " + e);
                        cb.onError(e);
                    }

                }
            });
        }

    }
}
