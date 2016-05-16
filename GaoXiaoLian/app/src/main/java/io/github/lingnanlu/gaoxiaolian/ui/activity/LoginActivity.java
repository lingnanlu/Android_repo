package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;


import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.ClientHelper;
import io.github.lingnanlu.gaoxiaolian.core.helper.UserHelper;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.model.User;

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

        btSignIn.setClickable(false);
        btSignUp.setClickable(false);

        UserHelper.login(name, password, new LoginCallBack());

    }

    @OnClick(R.id.bt_signUp)
    public void onRegisterClick(View view) {
        startActivity(RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    class LoginCallBack implements CallBack<User> {
        @Override
        public void onResult(User user) {


            //当应用登录成功时, 找开client, 即与服务器建立长连接, 这样, 就可以在应用的生命周期内
            //得到消息了
            ClientHelper.getInstance().open(user.getUsername(), new CallBack<Void>() {
                @Override
                public void onResult(Void result) {
                    btSignIn.setClickable(true);
                    btSignUp.setClickable(true);
                    startActivity(HomeActivity.class);
                    finish();
                }

                @Override
                public void onError(AVException e) {

                }
            });

        }

        @Override
        public void onError(AVException e) {
            Log.d(TAG, "onError: " + e.toString());
            btSignIn.setClickable(true);
            btSignUp.setClickable(true);
        }
    }


}
