package io.github.lingnanlu.gaoxiaolian.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import io.github.lingnanlu.gaoxiaolian.R;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    Button btRegister;
    EditText etName;
    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        btRegister = (Button) findViewById(R.id.bt_register);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_password);
        etPassword = (EditText) findViewById(R.id.et_password);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Log.d(TAG, "name " + name + " email " + email + " password " + password);
                AVUser user = new AVUser();

                user.setUsername(name);
                user.setEmail(email);
                user.setPassword(password);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {

                            //注册成功, 登录, 然后迁移到主界面
                            AVUser.logInInBackground(name, password, new LogInCallback<AVUser>() {
                                @Override
                                public void done(AVUser avUser, AVException e) {
                                    if( e == null) {
                                        startActivity(HomeActivity.class);
                                        finish();
                                    } else {
                                        //登陆失败
                                        Toast.makeText(RegisterActivity.this, "用户名或密码不正确", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
    }
}
