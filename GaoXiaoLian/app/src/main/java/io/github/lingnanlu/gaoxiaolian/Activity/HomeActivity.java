package io.github.lingnanlu.gaoxiaolian.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;

import org.w3c.dom.Text;

import io.github.lingnanlu.gaoxiaolian.R;

public class HomeActivity extends AppCompatActivity {

    TextView txName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txName = (TextView) findViewById(R.id.tx_name);

        AVUser user = AVUser.getCurrentUser();
        txName.setText(user.getUsername());

    }
}
