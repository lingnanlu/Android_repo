package io.github.lingnanlu.gaoxiaolian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;

import java.util.ArrayList;
import java.util.List;

import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.ViewHolder.CommonViewHolder;
import io.github.lingnanlu.gaoxiaolian.ViewHolder.LeftTextHolder;
import io.github.lingnanlu.gaoxiaolian.ViewHolder.RightTextHolder;

/**
 * Created by rico on 4/27/2016.
 */
public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int ITEM_LEFT_TEXT = 0;
    private final int ITEM_RIGHT_TEXT = 1;

    private List<AVIMMessage> messageList = new ArrayList<>();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_LEFT_TEXT) {
            return new LeftTextHolder(parent.getContext(), parent);
        } else if (viewType == ITEM_RIGHT_TEXT) {
            return new RightTextHolder(parent.getContext(), parent);
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommonViewHolder)holder).bindData(messageList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        AVIMMessage message = messageList.get(position);
        if (message.getFrom().equals(GaoXiaoLian.getClient().getClientId())) {
            return ITEM_RIGHT_TEXT;
        } else {
            return ITEM_LEFT_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public void setMessageList(List<AVIMMessage> messages) {
        messageList.clear();
        if (messages != null) {
            messageList.addAll(messages);
        }
    }

    public void addMessage(AVIMMessage message) {
        messageList.add(message);
    }
}
