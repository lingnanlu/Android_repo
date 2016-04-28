package io.github.lingnanlu.gaoxiaolian.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMConversation;

import io.github.lingnanlu.gaoxiaolian.InputBottomBar;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.adapter.MultipleItemAdapter;

public class ChatFragment extends Fragment {

    protected AVIMConversation conversation;
    protected RecyclerView recyclerView;
    protected MultipleItemAdapter itemAdapter;
    protected LinearLayoutManager layoutManager;
    protected InputBottomBar inputBottomBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setConversation(AVIMConversation conversation) {

    }
}
