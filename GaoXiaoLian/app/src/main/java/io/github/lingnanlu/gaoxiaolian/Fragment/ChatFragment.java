package io.github.lingnanlu.gaoxiaolian.Fragment;

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
import io.github.lingnanlu.gaoxiaolian.Event.ImTypeMessageEvent;
import io.github.lingnanlu.gaoxiaolian.Event.InputBottomBarTextEvent;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.adapter.MessageListAdapter;
import io.github.lingnanlu.gaoxiaolian.view.InputBottomBar;

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

        if (conversation != null) {
            conversation.queryMessages(new AVIMMessagesQueryCallback() {
                @Override
                public void done(List<AVIMMessage> list, AVIMException e) {
                    if (e == null) {
                        Log.d(TAG, "done: message fetch success");
                        itemAdapter.setMessageList(list);
                        recyclerView.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();
                        scrollToBottom();
                    }
                }
            });
        }
    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
    }

    public void onEvent(InputBottomBarTextEvent event) {
        Log.d(TAG, "onEvent: " + event.content);
        if (conversation != null && event != null) {
            if(!TextUtils.isEmpty(event.content)) {
                AVIMTextMessage message = new AVIMTextMessage();
                Map<String, Object> attrs = new HashMap<>();
                attrs.put("sender", GaoXiaoLian.getUser().getUsername());
                message.setAttrs(attrs);
                message.setText(event.content);
                itemAdapter.addMessage(message);
                itemAdapter.notifyDataSetChanged();
                scrollToBottom();
                conversation.sendMessage(message, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        //这里有什么用?
                        itemAdapter.notifyDataSetChanged();
                    }
                });
            }
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
