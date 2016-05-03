package io.github.lingnanlu.gaoxiaolian.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;

public class RegisterActivity extends BaseActivity{

    private static final String TAG = "RegisterActivity";

    User user;

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

    String[] schools;
    String[] sex;
    String[] status;

    @OnClick(R.id.bt_register)
    public void onRegisterClick(View view) {

        user.setUsername(etName.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.put(User.SN, etSN.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
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
        ButterKnife.bind(this);

        init();

        spSchool.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, schools));
        spSex.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sex));
        spStatus.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, status));

        spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put(User.SCHOOL, schools[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put(User.SEX, sex[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put(User.STATUS,status[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }


    private void init() {

        user = GaoXiaoLian.getUser();
        schools = getResources().getStringArray(R.array.school);
        sex = getResources().getStringArray(R.array.sex);
        status = getResources().getStringArray(R.array.status);

        //default value
        user.put(User.SCHOOL, schools[0]);
        user.put(User.SEX, sex[0]);
        user.put(User.STATUS,status[0]);
    }

}
