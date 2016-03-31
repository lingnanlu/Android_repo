package io.github.lingnanlu.leakhandler;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/29.
 */
public class XXXHelper {

    private Context mCtx;
    private TextView mTextView;

    private static XXXHelper instance = null;

    public static XXXHelper getInstance(Context context) {
        if (instance == null) {
            instance = new XXXHelper(context);
        }
        return instance;
    }


    private XXXHelper() {

    }

    public void setRetainedTextView(TextView tv) {
        this.mTextView = tv;
        mTextView.setText(mCtx.getString(R.string.OK));
    }
    private XXXHelper(Context context) {
        this.mCtx = context;
    }
}
