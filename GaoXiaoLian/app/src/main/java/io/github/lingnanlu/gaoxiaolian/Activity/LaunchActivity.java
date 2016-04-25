package io.github.lingnanlu.gaoxiaolian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import io.github.lingnanlu.gaoxiaolian.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 1500);

    }
}
