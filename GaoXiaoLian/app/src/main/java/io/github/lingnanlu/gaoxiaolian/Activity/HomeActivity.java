package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.tx_name) TextView txName;
    @Bind(R.id.bt_online) Button btOnline;
    @Bind(R.id.bt_edit) Button btEdit;
    @Bind(R.id.bt_personal) Button btPersonal;
    @Bind(R.id.bt_follower_followee) Button btFollowerFollowee;

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
        intent.putExtra(PersonalActivity.USERID, GaoXiaoLian.getUser().getObjectId());
        startActivity(intent);
    }

    @OnClick(R.id.bt_follower_followee)
    public void onFFClick(View view) {
        startActivity(FFActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        User user = GaoXiaoLian.getUser();

        txName.setText(user.getUsername());


    }
}
