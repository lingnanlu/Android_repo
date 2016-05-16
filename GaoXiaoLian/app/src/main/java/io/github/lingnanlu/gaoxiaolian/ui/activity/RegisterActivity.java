package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.avos.avoscloud.AVException;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.UserHelper;
import io.github.lingnanlu.gaoxiaolian.model.User;

public class RegisterActivity extends BaseActivity{

    User user;

    @Bind(R.id.bt_register) Button btRegister;
    @Bind(R.id.et_name) EditText etName;
    @Bind(R.id.et_email) EditText etEmail;
    @Bind(R.id.et_password) EditText etPassword;
    @Bind(R.id.sp_school) Spinner spSchool;
    @Bind(R.id.sp_sex) Spinner spSex;
    @Bind(R.id.sp_status) Spinner spStatus;
    @Bind(R.id.et_sn) EditText etSN;

    @OnClick(R.id.bt_register)
    public void onRegisterClick(View view) {

        user.put(User.SCHOOL, spSchool.getSelectedItem().toString());
        user.put(User.SEX, spSex.getSelectedItem().toString());
        user.put(User.STATUS, spStatus.getSelectedItem().toString());

        user.setUsername(etName.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.put(User.SN, etSN.getText().toString());

        UserHelper.register(user, new RegisterCallback());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        user = new User();
        /*
         * simple_spinner_dropdown_item 指的是弹出后,每一个Item的样式
         * simple_spinner_item 指的是选中某一个Item后, 在spinner中显示的样式
         */
//        spSchool.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schools));
//        spSex.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sex));
//        spStatus.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, status));
        spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * 以下两种方法都能获得所选条目上的数据, 但哪种是正确的方法呢?
                 *
                 * view : 所以所选中的view, 得到其文字, 也就得到其内容
                 * getItemAtPosition的方式和Adapter的原理有关, 其返回的是与点击view所关联的数据, 这里自动处理了header与footer的情况
                 * 所以如果只想获得view上显示的文字, 使用第一种方式就可以
                 * 如果想获得view上关联的那条数据的更好信息, 使用第二种方式更好
                 */
                Log.d(TAG, "onItemSelected: view " + ((TextView) view).getText());
                Log.d(TAG, "onItemSelected: parent " + parent.getItemAtPosition(position));
                user.put(User.SCHOOL, parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put(User.SEX, parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put(User.STATUS,parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    class RegisterCallback implements CallBack<Void> {

        @Override
        public void onResult(Void result) {
            startActivity(LoginActivity.class);
            finish();
        }

        @Override
        public void onError(AVException e) {
            Log.d(TAG, "onError: " + e.toString());
        }
    }
}
