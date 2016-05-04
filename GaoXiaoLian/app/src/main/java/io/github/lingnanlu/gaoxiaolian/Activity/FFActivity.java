package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFriendship;
import com.avos.avoscloud.AVFriendshipQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.callback.AVFriendshipCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;
import io.github.lingnanlu.gaoxiaolian.adapter.UserListAdapter;

public class FFActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    List<User> followers;
    List<User> followees;
    UserListAdapter userListAdapter;
    User user;

    @Bind(R.id.bt_follower)
    Button btFollower;

    @Bind(R.id.bt_followee)
    Button btFollowee;

    @Bind(R.id.lv_users)
    ListView lvUsers;

    @OnClick(R.id.bt_follower)
    public void onFollowerClick(View view) {

        userListAdapter.setUsers(followers);
        lvUsers.setAdapter(userListAdapter);

    }

    @OnClick(R.id.bt_followee)
    public void onFolloweeClick(View view) {
        userListAdapter.setUsers(followees);
        lvUsers.setAdapter(userListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ff);

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
                    btFollower.performClick();
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
}
