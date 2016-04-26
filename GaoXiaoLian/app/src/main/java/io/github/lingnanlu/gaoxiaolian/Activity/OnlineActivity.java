package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.List;
import java.util.zip.Inflater;

import io.github.lingnanlu.gaoxiaolian.R;

public class OnlineActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    ListView lvOnlineUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line);

        lvOnlineUsers = (ListView) findViewById(R.id.list_online_users);

        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if( e == null ) {
                    Log.d(TAG, "done: user list" + list);
                    lvOnlineUsers.setAdapter(new OnlineUserAdapter(OnlineActivity.this, list));
                    lvOnlineUsers.setOnItemClickListener(OnlineActivity.this);
                } else {
                    Log.d(TAG, "error");
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AVUser user = (AVUser) parent.getItemAtPosition(position);

        if (user != null) {
            Intent intent = new Intent(this, ConversationActivity.class);
            intent.putExtra("username", user.getObjectId());
            startActivity(intent);
        }
    }


    private static class OnlineUserAdapter extends BaseAdapter {

        LayoutInflater inflater;
        List<AVUser> users;

        public OnlineUserAdapter(Context context, List<AVUser> users) {
            inflater = LayoutInflater.from(context);
            this.users = users;
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
                convertView = inflater.inflate(R.layout.item_user, null);


                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.tx_name);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(users.get(position).getUsername());

            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }
}
