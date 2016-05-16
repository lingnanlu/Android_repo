package io.github.lingnanlu.gaoxiaolian.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.model.User;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OnlineAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public OnlineAdapter(Context context, List<User> users) {
        inflater = LayoutInflater.from(context);
        this.users = users;
    }

    public OnlineAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if (users != null) return users.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {

        if (users != null ) return users.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_online, null);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tx_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = users.get(position);

        viewHolder.name.setText(user.getUsername());
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView bubble_time;
    }
}
