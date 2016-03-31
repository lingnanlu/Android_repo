package io.github.lingnanlu.leakhandler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mLeakyHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "handleMessage");
//            }
//        }, 1000 * 10);
//
//        finish();

        TextView tv = (TextView) findViewById(R.id.tv_test);
        XXXHelper.getInstance(this).setRetainedTextView(tv);

    }

//    private final Handler mLeakyHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {}
//    };

//    private static class MyHandler extends Handler {
//
//        private final WeakReference<MainActivity> mActivity;
//
//        public MyHandler(MainActivity activity) {
//            mActivity = new WeakReference<MainActivity>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            MainActivity activity = mActivity.get();
//
//        }
//    }
}
