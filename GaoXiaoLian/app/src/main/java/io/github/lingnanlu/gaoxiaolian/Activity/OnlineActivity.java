package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import butterknife.Bind;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;
import io.github.lingnanlu.gaoxiaolian.adapter.UserListAdapter;

// TODO: 2016/5/4 online还未实现, SDK并没有提供得到在线用户的接口
// 暂时可以想到, 利用用户的冒泡时间来模拟在线功能, 根据用户的冒泡时间来进行排序
public class OnlineActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @Bind(R.id.list_online_users)
    ListView lvOnlineUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line);

        AVQuery<User> userQuery = User.getUserQuery(User.class);
        userQuery.whereNotEqualTo(User.OBJECT_ID, GaoXiaoLian.getUser().getObjectId());
        userQuery.orderByDescending(User.BUBBLE_TIME);

        userQuery.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if( e == null ) {
                    Log.d(TAG, "done: self list" + list);
                    lvOnlineUsers.setAdapter(new UserListAdapter(OnlineActivity.this, list));
                    lvOnlineUsers.setOnItemClickListener(OnlineActivity.this);
                } else {
                    Log.d(TAG, "done: get self list failed");
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        User user = (User) parent.getItemAtPosition(position);

        if (user != null) {
            Intent intent = new Intent(this, PersonalActivity.class);
//            intent.putExtra(PersonalActivity.USERID, user.getObjectId());
            intent.putExtra(PersonalActivity.USER, user);
            startActivity(intent);
        }
    }



}
