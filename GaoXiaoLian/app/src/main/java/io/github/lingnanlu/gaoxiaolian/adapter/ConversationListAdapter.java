package io.github.lingnanlu.gaoxiaolian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.List;

import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.POJO.User;
import io.github.lingnanlu.gaoxiaolian.R;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ConversationListAdapter extends BaseAdapter{

    private static final String TAG = "ConversationListAdapter";
    LayoutInflater inflater;
    List<AVIMConversation> convs;

    User self;
    public ConversationListAdapter(Context context, List<AVIMConversation> convs) {
        this.inflater = LayoutInflater.from(context);
        this.convs = convs;
        self = GaoXiaoLian.getUser();
    }

    @Override
    public int getCount() {
        if (convs != null) {
            return convs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(convs != null) return convs.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_conversation, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.lastMsg = (TextView) convertView.findViewById(R.id.tv_last_msg);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AVIMConversation conversation = convs.get(position);
        List<String> members = conversation.getMembers();

        String member0 = members.get(0);
        String member1 = members.get(1);

        String another = null;
        if (self.getObjectId().equals(member0)) {
            another = member1;
        } else {
            another = member0;
        }


        AVQuery<User> query = new AVQuery<>("_User");


        query.getInBackground(another, new GetCallback<User>() {
            @Override
            public void done(User user, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: get user success");
                    viewHolder.name.setText(user.getUsername());
                } else {
                    Log.d(TAG, "done: get user failed");
                }
            }
        });

        conversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
            @Override
            public void done(AVIMMessage avimMessage, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "done: get last message success " + avimMessage);
                    if (avimMessage != null) {
                        viewHolder.lastMsg.setText(((AVIMTextMessage)avimMessage).getText());
                    }
                } else {
                    Log.d(TAG, "done: get last message failed ");
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView lastMsg;
    }
}
