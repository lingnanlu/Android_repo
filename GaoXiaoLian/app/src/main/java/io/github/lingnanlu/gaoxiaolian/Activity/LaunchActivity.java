package io.github.lingnanlu.gaoxiaolian.Activity;

import android.os.Bundle;
import android.os.Handler;

import io.github.lingnanlu.gaoxiaolian.R;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
                finish();
            }
        }, 1500);

    }


}
