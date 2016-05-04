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
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;
/*
 * 该Activity有三种情况
 * 1. 用户本身的信息界面
 * 2. 其它已关注用户的信息界面
 * 3. 其它未关注用户的信息界面
 */
public class PersonalActivity extends BaseActivity {

    //public static final String USERID = "userid";
    public static final String USER = "user";

    User user;

    @Bind(R.id.tx_name) TextView txName;
    @Bind(R.id.tx_brief) TextView txBrief;
    @Bind(R.id.tx_contact) TextView txContact;
    @Bind(R.id.tx_interest) TextView txInterest;
    @Bind(R.id.tx_motto) TextView txMotto;
    @Bind(R.id.tx_private) TextView txPrivate;
    @Bind(R.id.tx_school) TextView txSchool;
    @Bind(R.id.tx_sn) TextView txSN;

    @Bind(R.id.bt_follow) Button btFollow;
    @Bind(R.id.bt_unfollow) Button btUnFollow;
    @Bind(R.id.bt_send_private_msg) Button btSendPrivateMsg;
    @Bind(R.id.bt_edit) Button btEdit;

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
        Intent intent = new Intent(PersonalActivity.this, ChatActivity.class);
        //intent.putExtra("self", self);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        //hide all buttons
        btFollow.setVisibility(View.GONE);
        btUnFollow.setVisibility(View.GONE);
        btSendPrivateMsg.setVisibility(View.GONE);
        btEdit.setVisibility(View.GONE);

        Intent intent = getIntent();

        //String userId = intent.getStringExtra(USERID);
        user = intent.getParcelableExtra(USER);
        //显示的用户为自己
        if (user == GaoXiaoLian.getUser()) {

            btEdit.setVisibility(View.VISIBLE);

        } else {

            //User并没有创建自己的CREATOR, 所以传递Parcelable时会出错
            //self = intent.getParcelableExtra("userdata");

            //user = intent.getParcelableExtra(USERDATA);

            AVQuery<User> followeeQuery = null;
            try {
                followeeQuery = GaoXiaoLian.getUser().followeeQuery(User.class);
                followeeQuery.findInBackground(new FindCallback<User>() {
                    @Override
                    public void done(List<User> followees, AVException e) {
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

        fillTextViewByUser(user);

    }

    private void fillTextViewByUser(User user) {

        if (user != null) {
            txName.setText(user.getUsername());
            txBrief.setText(user.getString(User.BRIEF));
            txContact.setText(user.getString(User.CONTACT));
            txInterest.setText(user.getString(User.INTEREST));
            txMotto.setText(user.getString(User.MOTTO));
            txSchool.setText(user.getString(User.SCHOOL));
            txSN.setText(user.getString(User.SN));

            //是用户本人
            if (user == GaoXiaoLian.getUser()) {
                txPrivate.setText(user.getString(User.PRIVATE));
            }
        } else {
            Log.d(TAG, "fillTextViewByUser: user is null");

        }

    }

}
