package io.github.lingnanlu.gaoxiaolian.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.ConversationHelper;
import io.github.lingnanlu.gaoxiaolian.event.ImTypeMessageEvent;
import io.github.lingnanlu.gaoxiaolian.event.InputBottomBarTextEvent;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.ui.adapter.MessageListAdapter;
import io.github.lingnanlu.gaoxiaolian.ui.view.InputBottomBar;

public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";
    protected AVIMConversation conversation;
    protected RecyclerView recyclerView;
    protected MessageListAdapter itemAdapter;
    protected LinearLayoutManager layoutManager;
    protected InputBottomBar inputBottomBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_chat);
        inputBottomBar = (InputBottomBar) view.findViewById(R.id.bottom_bar);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter = new MessageListAdapter();
        recyclerView.setAdapter(itemAdapter);

        EventBus.getDefault().register(this);
        return view;
    }

    public void setConversation(AVIMConversation conversation) {

        if (conversation != null) {
            this.conversation = conversation;
            fetchMessages();
        }
    }

    private void fetchMessages() {

        ConversationHelper.queryMessages(conversation, new CallBack<List<AVIMMessage>>() {
            @Override
            public void onResult(List<AVIMMessage> result) {
                itemAdapter.setMessageList(result);
                recyclerView.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
                scrollToBottom();
            }

            @Override
            public void onError(AVException e) {

            }
        });
    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
    }

    public void onEvent(InputBottomBarTextEvent event) {

        Log.d(TAG, "onEvent: " + event.content);

        if (event != null && !TextUtils.isEmpty(event.content)) {
            AVIMTextMessage message = new AVIMTextMessage();
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("sender", GaoXiaoLian.getUser().getUsername());
            message.setAttrs(attrs);
            message.setText(event.content);
            itemAdapter.addMessage(message);
            itemAdapter.notifyDataSetChanged();
            scrollToBottom();
            ConversationHelper.sendMessage(conversation, message, new CallBack<Void>() {
                @Override
                public void onResult(Void result) {
                    itemAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(AVException e) {

                }
            });
        }
    }

    public void onEvent(ImTypeMessageEvent event) {
        if (conversation != null && event != null) {
            itemAdapter.addMessage(event.message);
            itemAdapter.notifyDataSetChanged();
            scrollToBottom();
        }
    }
}
