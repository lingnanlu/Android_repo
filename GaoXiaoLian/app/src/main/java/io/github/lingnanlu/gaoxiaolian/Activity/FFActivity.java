package io.github.lingnanlu.gaoxiaolian.Activity;

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
import com.avos.avoscloud.AVFriendship;
import com.avos.avoscloud.AVFriendshipQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.callback.AVFriendshipCallback;

import java.util.List;

import butterknife.Bind;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.POJO.User;
import io.github.lingnanlu.gaoxiaolian.adapter.UserListAdapter;

public class FFActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    List<User> followers;
    List<User> followees;
    UserListAdapter userListAdapter;
    User user;

//    @Bind(R.id.bt_follower)
//    Button btFollower;
//
//    @Bind(R.id.bt_followee)
//    Button btFollowee;

    @Bind(R.id.lv_users)
    ListView lvUsers;
//
//    @OnClick(R.id.bt_follower)
//    public void onFollowerClick(View view) {
//
//        userListAdapter.setUsers(followers);
//        lvUsers.setAdapter(userListAdapter);
//
//    }
//
//    @OnClick(R.id.bt_followee)
//    public void onFolloweeClick(View view) {
//        userListAdapter.setUsers(followees);
//        lvUsers.setAdapter(userListAdapter);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ff);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        user = GaoXiaoLian.getUser();
        userListAdapter = new UserListAdapter(this);
        lvUsers.setOnItemClickListener(this);

        AVFriendshipQuery friendshipQuery = AVUser.friendshipQuery(user.getObjectId(), User.class);
        friendshipQuery.include("followee");
        friendshipQuery.include("followee");

        friendshipQuery.getInBackground(new AVFriendshipCallback() {
            @Override
            public void done(AVFriendship avFriendship, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: friendship get success ");
                    followers = avFriendship.getFollowers();
                    followees = avFriendship.getFollowees();
                    Log.d(TAG, "done: followers " + followers);
                    Log.d(TAG, "done: followee " + followees);
                    //btFollower.performClick();
                } else {
                    Log.d(TAG, "done: friendship get failed");
                }

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        User user = (User) parent.getItemAtPosition(position);

        if (user != null) {
            Intent intent = new Intent(this, PersonalActivity.class);
         //   intent.putExtra(PersonalActivity.USERID, user.getObjectId());
            intent.putExtra(PersonalActivity.USER, user);
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
