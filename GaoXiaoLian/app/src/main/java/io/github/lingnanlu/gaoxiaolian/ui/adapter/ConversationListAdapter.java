package io.github.lingnanlu.gaoxiaolian.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.ConversationHelper;
import io.github.lingnanlu.gaoxiaolian.model.User;
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
        if (self.getUsername().equals(member0)) {
            another = member1;
        } else {
            another = member0;
        }

        viewHolder.name.setText(another);
        ConversationHelper.getLastMessage(conversation, new CallBack<AVIMMessage>() {
            @Override
            public void onResult(AVIMMessage result) {
                if (result != null) {
                    viewHolder.lastMsg.setText(((AVIMTextMessage) result).getText());
                }
            }

            @Override
            public void onError(AVException e) {

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView lastMsg;
    }
}
