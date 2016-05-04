package io.github.lingnanlu.gaoxiaolian.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.User;

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

    @Bind(R.id.bt_commit)
    Button btCommit;

    @OnClick(R.id.bt_commit)
    public void onCommit(View view) {

        fillUser();

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: save success");
                    finish();
                } else {
                    Log.d(TAG, "done: save failed " + e.toString());
                }
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

        //ButterKnife.bind(this);


        user = GaoXiaoLian.getUser();

        fillEditText();

    }




//    private void init() {
//        map = new HashMap<>();
//
//        LinearLayout editLayout = (LinearLayout) findViewById(R.id.edit_layout);
//        int childCount = editLayout.getChildCount();
//        for(int i = 0; i < childCount; i++) {
//            View child = editLayout.getChildAt(i);
//            if (child instanceof EditText) {
//                map.put(((EditText) child).getHint(), (EditText) child);
//            }
//        }
//
//    }

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
