package io.github.lingnanlu.gaoxiaolian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.List;

import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;

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

        List<String> members = convs.get(position).getMembers();

        String name = null;
        for (String member : members) {
            if(!member.equals(self.getObjectId())){
                name = member;
                break;
            }
        }
        viewHolder.name.setText(name);
        convs.get(position).getLastMessage(new AVIMSingleMessageQueryCallback() {
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
