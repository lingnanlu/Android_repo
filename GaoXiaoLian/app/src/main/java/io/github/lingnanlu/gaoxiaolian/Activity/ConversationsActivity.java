package io.github.lingnanlu.gaoxiaolian.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.ListView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;
import io.github.lingnanlu.gaoxiaolian.adapter.ConversationListAdapter;

public class ConversationsActivity extends BaseActivity {

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
                } else {
                    Log.d(TAG, "done: conversation list get failed");
                }
            }
        });
    }


}
