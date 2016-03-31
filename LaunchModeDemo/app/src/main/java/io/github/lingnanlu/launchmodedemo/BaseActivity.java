package io.github.lingnanlu.launchmodedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void startStandardActivity(View v) {
        startActivity(new Intent(this, StandardActivity.class));
    }

    public void startSingleTaskActivity(View v) {
        startActivity(new Intent(this, SingleTaskActivity.class));
    }

    public void startSingleInstanceActivity(View v) {
        startActivity(new Intent(this, SingleInstanceActivity.class));
    }

}
