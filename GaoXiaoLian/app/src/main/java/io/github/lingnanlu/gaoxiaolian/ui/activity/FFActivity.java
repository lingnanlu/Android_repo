package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;

import java.util.List;

import butterknife.Bind;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.UserHelper;
import io.github.lingnanlu.gaoxiaolian.model.User;
import io.github.lingnanlu.gaoxiaolian.ui.adapter.UserListAdapter;

public class FFActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    List<User> followers;           //关注我的人
    List<User> followees;           //我关注的人

    UserListAdapter userListAdapter;
    User user;

    @Bind(R.id.lv_users)
    ListView lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ff);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        user = GaoXiaoLian.getUser();
        userListAdapter = new UserListAdapter(this);
        lvUsers.setOnItemClickListener(this);

        UserHelper.getFollowers(new CallBack<List<User>>() {
            @Override
            public void onResult(List<User> result) {
                followers = result;
                userListAdapter.setUsers(followers);
                lvUsers.setAdapter(userListAdapter);
            }

            @Override
            public void onError(AVException e) {

            }
        });

        UserHelper.getFollowees(new CallBack<List<User>>(){

            @Override
            public void onResult(List<User> result) {
                followees = result;
            }

            @Override
            public void onError(AVException e) {

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        User user = (User) parent.getItemAtPosition(position);

        if (user != null) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            intent.putExtra(UserInfoActivity.USERID, user.getObjectId());
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ff, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_follower:
                Log.d(TAG, "onOptionsItemSelected: follower clicked");
                userListAdapter.setUsers(followers);
                lvUsers.setAdapter(userListAdapter);
                break;
            case R.id.action_followee:
                Log.d(TAG, "onOptionsItemSelected: followee clicked");
                userListAdapter.setUsers(followees);
                lvUsers.setAdapter(userListAdapter);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
