package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;

import java.util.List;

import butterknife.Bind;
import io.github.lingnanlu.gaoxiaolian.R;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.core.helper.UserHelper;
import io.github.lingnanlu.gaoxiaolian.model.User;
import io.github.lingnanlu.gaoxiaolian.ui.adapter.OnlineAdapter;

//利用User.STATUS字段来模拟在线功能
public class OnlineActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.list_online_users)
    ListView lvOnlineUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        UserHelper.getOnlineUsers(new CallBack<List<User>>() {
            @Override
            public void onResult(List<User> onlines) {
                if (onlines != null) {
                    lvOnlineUsers.setAdapter(new OnlineAdapter(OnlineActivity.this, onlines));
                    lvOnlineUsers.setOnItemClickListener(OnlineActivity.this);
                }

            }

            @Override
            public void onError(AVException e) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        User user = (User) parent.getItemAtPosition(position);

        if (user != null) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            intent.putExtra(UserInfoActivity.USERID, user.getObjectId());
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sex, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_male:
                Log.d(TAG, "onOptionsItemSelected: male clicked");
                break;
            case R.id.action_female:
                Log.d(TAG, "onOptionsItemSelected: female clicked");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
