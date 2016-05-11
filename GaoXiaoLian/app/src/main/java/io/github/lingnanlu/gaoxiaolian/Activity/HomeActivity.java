package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.POJO.ExternalData;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.POJO.User;

public class HomeActivity extends BaseActivity {

    User self;
    @Bind(R.id.tx_name) TextView tvName;
    @Bind(R.id.tv_visit_num) TextView tvVisitNum;
    @Bind(R.id.tv_like_num) TextView tvLikeNum;
    @Bind(R.id.bt_online) Button btOnline;
    @Bind(R.id.bt_edit) Button btEdit;
    @Bind(R.id.bt_personal) Button btPersonal;
    @Bind(R.id.bt_follower_followee) Button btFollowerFollowee;
    @Bind(R.id.bt_private_msg) Button btPrivateMsg;

    @OnClick(R.id.bt_online)
    public void onOnlineClick(View view) {
        startActivity(OnlineActivity.class);
    }

    @OnClick(R.id.bt_edit)
    public void onEditClick(View view) {
        startActivity(EditActivity.class);
    }

    @OnClick(R.id.bt_personal)
    public void onPersonalClick(View view) {
        Intent intent = new Intent(this, PersonalActivity.class);
        intent.putExtra(PersonalActivity.USER, GaoXiaoLian.getUser());
        startActivity(intent);
    }

    @OnClick(R.id.bt_follower_followee)
    public void onFFClick(View view) {
        startActivity(FFActivity.class);
    }

    @OnClick(R.id.bt_private_msg)
    public void onPrivateMsg(View view) {
        startActivity(ConversationsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        self = GaoXiaoLian.getUser();
        tvName.setText(self.getUsername());
        AVRelation<ExternalData> relation = self.getRelation(User.EXTERNAL_DATA);
        AVQuery<ExternalData> query = relation.getQuery();
        query.findInBackground(new FindCallback<ExternalData>() {
            @Override
            public void done(List<ExternalData> list, AVException e) {
                if (filter(e)) {
                    ExternalData data = list.get(0);
                    tvVisitNum.setText("访客数:" + data.getInt(ExternalData.VISIT_NUM));
                    tvLikeNum.setText("获赞数:" + data.getInt(ExternalData.LIKE_NUM));
                }
            }
        });

    }


}
