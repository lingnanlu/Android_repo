package io.github.lingnanlu.pushdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushService.setDefaultPushCallback(this, MainActivity.class);

        PushService.subscribe(this, "public", MainActivity.class);

        final Context context = this;

        final TextView t = (TextView) findViewById(R.id.mylabel);

        t.setText("this device id: " + AVInstallation.getCurrentInstallation().getInstallationId());

        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: saved success");
                } else {
                    Log.d(TAG, "done: saved failed " + e);

                }
            }
        });

        final EditText channelEdit = (EditText) findViewById(R.id.channel);
        EditText msgEdit = (EditText) findViewById(R.id.message);
        Button btn = (Button) findViewById(R.id.pushBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVPush push = new AVPush();

                push.setChannel(channelEdit.getText().toString().trim());
                push.set
            }
        });
    }
}
