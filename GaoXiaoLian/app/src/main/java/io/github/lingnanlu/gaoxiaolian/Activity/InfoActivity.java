package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;

public class InfoActivity extends BaseActivity {

    AVUser user = null;

    @Bind(R.id.tx_name) TextView txName;
    @Bind(R.id.bt_follow) Button btFollow;
    @Bind(R.id.bt_unfollow) Button btUnFollow;
   // @Bind(R.id.bt_ret_home) Button btRetHome;
    @Bind(R.id.bt_send_private_msg) Button btSendPrivateMsg;

    @OnClick(R.id.bt_follow)
    public void onFollowClick(View view) {
        GaoXiaoLian.getUser().followInBackground(user.getObjectId(), new FollowCallback() {

            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    btFollow.setVisibility(View.GONE);
                    btUnFollow.setVisibility(View.VISIBLE);
                    btSendPrivateMsg.setVisibility(View.VISIBLE);
                    Log.d(TAG, "done: follow success");
                } else {
                    Log.d(TAG, "done: follow failed");
                }
            }

        });
    }

    @OnClick(R.id.bt_unfollow)
    public void onUnFollowClick(View view) {
        GaoXiaoLian.getUser().unfollowInBackground(user.getObjectId(), new FollowCallback() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: unfollow success");
                    btFollow.setVisibility(View.VISIBLE);
                    btUnFollow.setVisibility(View.GONE);
                    btSendPrivateMsg.setVisibility(View.GONE);
                } else {
                    Log.d(TAG, "done: unfollow failed");
                }
            }
        });
    }

    @OnClick(R.id.bt_send_private_msg)
    public void onSendPrivateMsgClick(View view) {
        Intent intent = new Intent(InfoActivity.this, ChatActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

//    @OnClick(R.id.bt_ret_home)
//    public void onReturnHomeClick(View view) {
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);


        btFollow.setVisibility(View.GONE);
        btUnFollow.setVisibility(View.GONE);
        btSendPrivateMsg.setVisibility(View.GONE);

        user = getIntent().getParcelableExtra("user");
        txName.setText(user.getUsername());

        AVQuery<AVUser> followeeQuery = null;
        try {
            followeeQuery = GaoXiaoLian.getUser().followeeQuery(AVUser.class);
            followeeQuery.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> followees, AVException e) {
                    if (e == null) {
                        Log.d(TAG, "done: followees get success " + followees);

                        //如果已关注user
                        if (followees != null && followees.contains(user)) {
                            btSendPrivateMsg.setVisibility(View.VISIBLE);
                            btUnFollow.setVisibility(View.VISIBLE);
                        } else {
                            btFollow.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.d(TAG, "done: followees get failed");
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }

    }
}
