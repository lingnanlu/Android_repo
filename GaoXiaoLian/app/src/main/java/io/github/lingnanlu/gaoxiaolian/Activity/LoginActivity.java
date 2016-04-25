package io.github.lingnanlu.gaoxiaolian.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import io.github.lingnanlu.gaoxiaolian.R;

public class LoginActivity extends BaseActivity {

    Button btSignIn;
    Button btSignUp;
    EditText etName;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btSignIn = (Button) findViewById(R.id.bt_signIn);
        btSignUp = (Button) findViewById(R.id.bt_signUp);
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_password);

        btSignIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();

                AVUser.logInInBackground(name, password, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if( e == null) {
                            startActivity(HomeActivity.class);
                            finish();
                        } else {
                            //登陆失败
                            Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        btSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.class);
            }
        });
    }
}
