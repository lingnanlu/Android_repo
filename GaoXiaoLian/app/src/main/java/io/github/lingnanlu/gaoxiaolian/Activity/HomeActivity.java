package io.github.lingnanlu.gaoxiaolian.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;

import org.w3c.dom.Text;

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

    @OnClick(R.id.bt_online)
    public void onOnlineClick(View view) {
        startActivity(OnlineActivity.class);
    }

    @OnClick(R.id.bt_edit)
    public void onEditClick(View view) {

        startActivity(EditActivity.class);

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
