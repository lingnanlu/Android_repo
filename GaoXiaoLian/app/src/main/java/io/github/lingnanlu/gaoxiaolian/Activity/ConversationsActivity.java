package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.POJO.User;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.adapter.ConversationListAdapter;

public class ConversationsActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    User self;

    @Bind(R.id.list_convs)
    ListView lvConversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_message);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        self = GaoXiaoLian.getUser();
        AVIMConversationQuery query = GaoXiaoLian.getClient().getQuery();

        query.containsMembers(Arrays.asList(self.getObjectId()));
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: conversation list get success size" + list.size());
                    lvConversations.setAdapter(new ConversationListAdapter(ConversationsActivity
                            .this, list));
                    lvConversations.setOnItemClickListener(ConversationsActivity.this);
                } else {
                    Log.d(TAG, "done: conversation list get failed");
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AVIMConversation conversation = (AVIMConversation) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(ChatActivity.FROM, this.getClass().getSimpleName());
        intent.putExtra(ChatActivity.CONV_ID, conversation.getConversationId());
        startActivity(intent);


    }
}
