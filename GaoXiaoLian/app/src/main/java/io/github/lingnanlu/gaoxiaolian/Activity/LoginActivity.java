package io.github.lingnanlu.gaoxiaolian.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.bt_signIn)
    Button btSignIn;

    @Bind(R.id.bt_signUp)
    Button btSignUp;

    @Bind(R.id.et_name)
    EditText etName;

    @Bind(R.id.et_password)
    EditText etPassword;

    @OnClick(R.id.bt_signIn)
    public void onLoginClick(View view) {

        String name = etName.getText().toString();
        String password = etPassword.getText().toString();

        btSignIn.setEnabled(false);
        btSignUp.setEnabled(false);

        AVUser.logInInBackground(name, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser user, AVException e) {
                if( e == null) {

                    GaoXiaoLian.setUser(user);
                    final AVIMClient client = AVIMClient.getInstance(user.getObjectId());
                    client.open(new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (e == null) {
                                Log.d(TAG, "done: client open success");
                                GaoXiaoLian.setClient(client);
                                startActivity(HomeActivity.class);
                                finish();
                            } else {
                                Log.d(TAG, "done: client open failed");
                            }
                        }
                    });

                } else {
                    //登陆失败
                    Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick(R.id.bt_signUp)
    public void onRegisterClick(View view) {
        startActivity(RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }
}
