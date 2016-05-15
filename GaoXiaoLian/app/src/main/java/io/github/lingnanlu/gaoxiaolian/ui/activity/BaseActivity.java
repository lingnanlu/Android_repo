package io.github.lingnanlu.gaoxiaolian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.avos.avoscloud.AVException;

import butterknife.ButterKnife;
import io.github.lingnanlu.gaoxiaolian.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        //要在setContentView之后才能调用
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean filter(AVException e) {
        if(e == null) {
            Log.d(TAG, "success");
            return true;
        } else {
            Log.d(TAG, "failed " + e.toString());
            return false;
        }
    }

}
