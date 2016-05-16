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
import io.github.lingnanlu.gaoxiaolian.model.Online;
import io.github.lingnanlu.gaoxiaolian.model.User;
import io.github.lingnanlu.gaoxiaolian.ui.adapter.OnlineAdapter;

// TODO: 2016/5/4 online还未实现, SDK并没有提供得到在线用户的接口
// 暂时可以想到, 利用用户的冒泡时间来模拟在线功能, 根据用户的冒泡时间来进行排序
public class OnlineActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @Bind(R.id.list_online_users)
    ListView lvOnlineUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        UserHelper.getOnlineUsers(new CallBack<List<Online>>() {
            @Override
            public void onResult(List<Online> onlines) {
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

        Online online = (Online) parent.getItemAtPosition(position);
        try {
            User user = online.getAVObject(Online.USER, User.class);
            if (user != null) {
                Intent intent = new Intent(this, UserInfoActivity.class);
                intent.putExtra(UserInfoActivity.USERID, user.getObjectId());
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
