package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.UserHelper;
import io.github.lingnanlu.gaoxiaolian.model.User;
/*
 * 该Activity有三种情况
 * 1. 用户本身的信息界面
 * 2. 其它已关注用户的信息界面
 * 3. 其它未关注用户的信息界面
 *
 * 对不同的情况要显示不同的view以及内容
 */
public class UserInfoActivity extends BaseActivity {

    public static final String USERID = "userid";

    String mUserID;

    @Bind(R.id.tx_name) TextView txName;
    @Bind(R.id.tx_brief) TextView txBrief;
    @Bind(R.id.tx_contact) TextView txContact;
    @Bind(R.id.tx_interest) TextView txInterest;
    @Bind(R.id.tx_motto) TextView txMotto;
    @Bind(R.id.tx_private) TextView txPrivate;
    @Bind(R.id.tx_school) TextView txSchool;
    @Bind(R.id.tx_sn) TextView txSN;
    @Bind(R.id.tv_sign_up_time) TextView tvSignUpTime;
    @Bind(R.id.tv_bubble_time) TextView tvBubbleTime;

    @Bind(R.id.bt_follow) Button btFollow;
    @Bind(R.id.bt_unfollow) Button btUnFollow;
    @Bind(R.id.bt_send_private_msg) Button btSendPrivateMsg;
    @Bind(R.id.bt_edit) Button btEdit;
    @Bind(R.id.bt_like) Button btLike;

    @OnClick(R.id.bt_follow)
    public void onFollowClick(View view) {

        UserHelper.follow(mUserID, new CallBack<Void>() {
            @Override
            public void onResult(Void result) {
                btFollow.setVisibility(View.GONE);
                btUnFollow.setVisibility(View.VISIBLE);
                btSendPrivateMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(AVException e) {

            }
        });
    }

    @OnClick(R.id.bt_unfollow)
    public void onUnFollowClick(View view) {
        UserHelper.unFollow(mUserID, new CallBack<Void>() {
            @Override
            public void onResult(Void result) {
                btFollow.setVisibility(View.VISIBLE);
                btUnFollow.setVisibility(View.GONE);
                btSendPrivateMsg.setVisibility(View.GONE);
            }

            @Override
            public void onError(AVException e) {

            }
        });
    }

    @OnClick(R.id.bt_like)
    public void onLikeClick(View view) {

        UserHelper.like(mUserID, new CallBack<Void>() {
            @Override
            public void onResult(Void result) {
                UserHelper.getLikeCount(mUserID, new CallBack<Integer>() {
                    @Override
                    public void onResult(Integer likeCount) {
                        btLike.setText(likeCount + "");
                    }

                    @Override
                    public void onError(AVException e) {

                    }
                });
            }

            @Override
            public void onError(AVException e) {

                if(e.getCode() == AVException.DUPLICATE_VALUE){
                    Toast.makeText(UserInfoActivity.this, "您已经点过赞了", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @OnClick(R.id.bt_edit)
    public void onEditClick(View view) {
        startActivity(EditActivity.class);
    }

    @OnClick(R.id.bt_send_private_msg)
    public void onSendPrivateMsgClick(View view) {
//        Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
//        intent.putExtra(USER, user);
//        intent.putExtra(ChatActivity.FROM, this.getClass().getSimpleName());
//        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        hideViews();

        Intent intent = getIntent();
        mUserID = intent.getStringExtra(USERID);
        UserHelper.visit(mUserID, new CallBack<Void>() {
            @Override
            public void onResult(Void result) {

            }

            @Override
            public void onError(AVException e) {

            }
        });

        UserHelper.getUser(mUserID, new GetUserCallBack());

    }

    private void hideViews() {

        txPrivate.setVisibility(View.GONE);
        btFollow.setVisibility(View.GONE);
        btSendPrivateMsg.setVisibility(View.GONE);
        btUnFollow.setVisibility(View.GONE);
        btEdit.setVisibility(View.GONE);

    }

    class GetUserCallBack implements CallBack<User> {

        @Override
        public void onResult(User user) {
            txName.setText(user.getUsername());
            txBrief.setText(user.getString(User.BRIEF));
            txContact.setText(user.getString(User.CONTACT));
            txInterest.setText(user.getString(User.INTEREST));
            txMotto.setText(user.getString(User.MOTTO));
            txSchool.setText(user.getString(User.SCHOOL));
            txSN.setText(user.getString(User.SN));

            UserHelper.getLikeCount(user.getObjectId(), new CallBack<Integer>() {
                @Override
                public void onResult(Integer likeCount) {
                    btLike.setText(likeCount + "");
                }

                @Override
                public void onError(AVException e) {

                }
            });

            if (user.getObjectId().equals(GaoXiaoLian.getUser().getObjectId())) {
                btLike.setClickable(false);
                txPrivate.setVisibility(View.VISIBLE);
                txPrivate.setText(user.getString(User.PRIVATE));
                btEdit.setVisibility(View.VISIBLE);
            } else {
                UserHelper.hasFollowed(user.getObjectId(), new QueryFollowCallback());
            }
        }

        @Override
        public void onError(AVException e) {

        }
    }

    class QueryFollowCallback implements CallBack<Boolean> {
        @Override
        public void onResult(Boolean hasFollowed) {


            if (hasFollowed == true) {
                btUnFollow.setVisibility(View.VISIBLE);
                btSendPrivateMsg.setVisibility(View.VISIBLE);
            } else {
                btFollow.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onError(AVException e) {
            Log.d(TAG, "onError: " + e);
        }
    }





}
