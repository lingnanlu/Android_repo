package io.github.lingnanlu.pushdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SendCallback;

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
        final EditText msgEdit = (EditText) findViewById(R.id.message);
        Button btn = (Button) findViewById(R.id.pushBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVPush push = new AVPush();

                push.setChannel(channelEdit.getText().toString().trim());
                push.setMessage(msgEdit.getText().toString().trim());
                push.setQuery(AVInstallation.getQuery().whereEqualTo("installationId",
                        AVInstallation.getCurrentInstallation().getInstallationId()));

                push.sendInBackground(new SendCallback() {
                    @Override
                    public void done(AVException e) {
                        Toast toast = null;
                        if (e == null) {
                            toast = Toast.makeText(context, "Send successfully.", Toast
                                    .LENGTH_SHORT);
                        } else {
                            toast =
                                    Toast.makeText(context, "Send fails with :" + e.getMessage(),
                                            Toast.LENGTH_LONG);
                        }
                        // 放心大胆地show，我们保证 callback 运行在 UI 线程。
                        toast.show();
                    }
                });
            }
        });

        View customPushButton = this.findViewById(R.id.customPush);
        customPushButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AVPush push = new AVPush();

                AVQuery<AVInstallation> query = AVInstallation.getQuery();
                query.whereEqualTo("installationId", AVInstallation.getCurrentInstallation()
                        .getInstallationId());
                push.setQuery(query);
                push.setChannel(channelEdit.getText().toString().trim());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("action", "com.pushdemo.action");
                jsonObject.put("alert", msgEdit.getText().toString().trim());

                push.setData(jsonObject);
                push.setPushToAndroid(true);
                push.sendInBackground(new SendCallback() {
                    @Override
                    public void done(AVException e) {
                        Toast.makeText(getApplicationContext(), "send successfully", Toast
                                .LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}

