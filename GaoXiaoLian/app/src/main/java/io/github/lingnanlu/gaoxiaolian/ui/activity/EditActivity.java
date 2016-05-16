package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.UserHelper;
import io.github.lingnanlu.gaoxiaolian.model.User;

public class EditActivity extends BaseActivity {

    User user;

    @Bind(R.id.et_name) EditText etName;
    @Bind(R.id.et_sn) EditText etSN;
    @Bind(R.id.et_college) EditText etCollege;
    @Bind(R.id.et_height) EditText etHeight;
    @Bind(R.id.et_interest) EditText etInterest;
    @Bind(R.id.et_motto) EditText etMotto;
    @Bind(R.id.et_brief) EditText etBrief;
    @Bind(R.id.et_contact) EditText etContact;
    @Bind(R.id.et_private) EditText etPrivate;

    @Bind(R.id.bt_commit) Button btCommit;

    @OnClick(R.id.bt_commit)
    public void onCommit(View view) {

        fillUser();

        UserHelper.save(new CallBack<Void>() {
            @Override
            public void onResult(Void result) {
                Toast.makeText(EditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(AVException e) {

            }
        });

    }

    private void fillUser() {
        user.setUsername(etName.getText().toString());
        user.put(User.SN, etSN.getText().toString());
        user.put(User.COLLEGE, etCollege.getText().toString());
        user.put(User.HEIGHT, etHeight.getText().toString());
        user.put(User.INTEREST, etInterest.getText().toString());
        user.put(User.MOTTO, etMotto.getText().toString());
        user.put(User.BRIEF, etBrief.getText().toString());
        user.put(User.CONTACT, etContact.getText().toString());
        user.put(User.PRIVATE, etPrivate.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        user = GaoXiaoLian.getUser();

        fillEditText();

    }

    private void fillEditText() {

        // TODO: 2016/5/3 这里有代码的坏味道, 同一份信息, 多处重复了, 相办法消除
        etName.setText(user.getUsername());
        etSN.setText(user.getString(User.SN));
        etCollege.setText(user.getString(User.COLLEGE));
        etHeight.setText(user.getString(User.HEIGHT));
        etInterest.setText(user.getString(User.INTEREST));
        etMotto.setText(user.getString(User.MOTTO));
        etBrief.setText(user.getString(User.BRIEF));
        etContact.setText(user.getString(User.CONTACT));
        etPrivate.setText(user.getString(User.PRIVATE));
    }


}
