package io.github.lingnanlu.gaoxiaolian.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.model.Online;
import io.github.lingnanlu.gaoxiaolian.model.User;

/**
 * Created by Administrator on 2016/5/16.
 */
public class OnlineAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<Online> onlines;

    public void setUsers(List<Online> onlines) {
        this.onlines = onlines;
    }

    public OnlineAdapter(Context context, List<Online> onlines) {
        inflater = LayoutInflater.from(context);
        this.onlines = onlines;
    }

    public OnlineAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if (onlines != null) return onlines.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {

        if (onlines != null ) return onlines.get(position);
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

        Online online = onlines.get(position);
        try {
            User user = online.getAVObject(Online.USER, User.class);
            viewHolder.name.setText(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
