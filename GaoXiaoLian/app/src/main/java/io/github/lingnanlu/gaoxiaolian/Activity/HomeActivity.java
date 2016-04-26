package io.github.lingnanlu.gaoxiaolian.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;

import org.w3c.dom.Text;

import io.github.lingnanlu.gaoxiaolian.R;

public class HomeActivity extends BaseActivity {


    TextView txName;
    Button btOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txName = (TextView) findViewById(R.id.tx_name);
        btOnline = (Button) findViewById(R.id.bt_online);

        AVUser user = AVUser.getCurrentUser();

        txName.setText(user.getUsername());
        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                startActivity(OnlineActivity.class);
            }
        });

    }
}
