package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.POJO.ExternalData;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.POJO.User;
/*
 * 该Activity有三种情况
 * 1. 用户本身的信息界面
 * 2. 其它已关注用户的信息界面
 * 3. 其它未关注用户的信息界面
 *
 * 对不同的情况要显示不同的view以及内容
 */
public class PersonalActivity extends BaseActivity {

    //public static final String USERID = "userid";
    public static final String USER = "user";

    User user;
    Boolean hasFollowed = false;
    ExternalData data;
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

    @OnClick(R.id.bt_like)
    public void onLikeClick(View view) {

        int likeCount = data.getInt(ExternalData.LIKE_NUM);
        likeCount++;
        data.put(ExternalData.LIKE_NUM, likeCount);
        data.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (filter(e)) {
                    btLike.setText(data.getInt(ExternalData.LIKE_NUM) + "个赞");
                }
            }
        });
    }

    @OnClick(R.id.bt_unfollow)
    public void onUnFollowClick(View view) {
        user.unfollowInBackground(user.getObjectId(), new FollowCallback() {
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

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        init();

        displayCommonDisplay(user);

        //同步的得到其ExternalData与Follow信息
        AsyncTask<User, Void, Void> batchAsyncTask = new AsyncTask<User, Void, Void>() {

            @Override
            protected Void doInBackground(User... params) {
                User user = params[0];

                AVQuery<User> followerQuery = null;
                try {
                    followerQuery = user.followerQuery(User.class);
                    List<User> followers = followerQuery.find();
                    if (followers != null) {
                        for (User follower : followers) {
                            if (follower.getObjectId().equals(GaoXiaoLian.getUser().getObjectId())) {
                                hasFollowed = true;
                            }
                        }
                    } else {
                        hasFollowed = false;
                    }

                    AVRelation<ExternalData> relation = user.getRelation(User.EXTERNAL_DATA);
                    AVQuery<ExternalData> relationQuery = relation.getQuery();
                    List<ExternalData> externalDatas = relationQuery.find();
                    data = externalDatas.get(0);
                } catch (AVException e) {
                    Log.d(TAG, e.toString() );
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                btLike.setText("获赞数:" + data.getInt(ExternalData.LIKE_NUM));

                if (user.getUsername().equals(GaoXiaoLian.getUser().getUsername())) {

                    btLike.setClickable(false);
                    txPrivate.setVisibility(View.VISIBLE);
                    txPrivate.setText(user.getString(User.PRIVATE));
                    btEdit.setVisibility(View.VISIBLE);

                } else {

                    btLike.setClickable(true);
                    //更新访客数
                    int visit_num = data.getInt(ExternalData.VISIT_NUM);
                    Log.d(TAG, "done: visit_num " + visit_num);
                    visit_num++;
                    data.put(ExternalData.VISIT_NUM, visit_num);
                    data.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            filter(e);
                        }
                    });

                    if (hasFollowed) {
                        btUnFollow.setVisibility(View.VISIBLE);
                        btSendPrivateMsg.setVisibility(View.VISIBLE);
                    } else {
                        btFollow.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        batchAsyncTask.execute(user);

    }

    public void init(){

        //对于所以因不同情况显示不同的view, 做初始化
        btFollow.setVisibility(View.GONE);
        btUnFollow.setVisibility(View.GONE);
        btSendPrivateMsg.setVisibility(View.GONE);
        btEdit.setVisibility(View.GONE);

        btLike.setClickable(false);

        txPrivate.setVisibility(View.GONE);

        Intent intent = getIntent();

        //重新组装的User虽然内容一样, 但引用确不一样了.
        user = intent.getParcelableExtra(USER);
    }

    //不论是哪一种情况, 都要显示的信息
    public void displayCommonDisplay(User user) {

        txName.setText(user.getUsername());
        txBrief.setText(user.getString(User.BRIEF));
        txContact.setText(user.getString(User.CONTACT));
        txInterest.setText(user.getString(User.INTEREST));
        txMotto.setText(user.getString(User.MOTTO));
        txSchool.setText(user.getString(User.SCHOOL));
        txSN.setText(user.getString(User.SN));

    }

}
