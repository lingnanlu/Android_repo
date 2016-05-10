package io.github.lingnanlu.gaoxiaolian.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;

public class RegisterActivity extends BaseActivity{

    User self;

    @Bind(R.id.bt_register)
    Button btRegister;

    @Bind(R.id.et_name)
    EditText etName;

    @Bind(R.id.et_email)
    EditText etEmail;

    @Bind(R.id.et_password)
    EditText etPassword;

    @Bind(R.id.sp_school)
    Spinner spSchool;

    @Bind(R.id.sp_sex)
    Spinner spSex;

    @Bind(R.id.sp_status)
    Spinner spStatus;

    @Bind(R.id.et_sn)
    EditText etSN;

    @OnClick(R.id.bt_register)
    public void onRegisterClick(View view) {

        self.setUsername(etName.getText().toString());
        self.setPassword(etPassword.getText().toString());
        self.setEmail(etEmail.getText().toString());
        self.put(User.SN, etSN.getText().toString());

        self.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    startActivity(LoginActivity.class);
                    finish();
                } else {
                    Log.d(TAG, "done: " + e.toString());
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        self = GaoXiaoLian.getUser();

        self.put(User.SCHOOL, spSchool.getSelectedItem().toString());
        self.put(User.SEX, spSex.getSelectedItem().toString());
        self.put(User.STATUS, spStatus.getSelectedItem().toString());
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
                self.put(User.SCHOOL, parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                self.put(User.SEX, parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                self.put(User.STATUS,parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }


}
