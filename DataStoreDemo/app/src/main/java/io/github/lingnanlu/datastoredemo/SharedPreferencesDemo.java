package io.github.lingnanlu.datastoredemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SharedPreferencesDemo extends AppCompatActivity {

    private static final String TAG = "activity_shared_preferences";

    static final String SP_FILE_NAME = "sp_test";
    static final String SP_NAME = "sp_name";
    static final String SP_AGE = "sp_age";
    static final String SP_SINGLE = "sp_single";

    private SharedPreferences mPerf = null;
    private SharedPreferences.Editor mEditor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        mPerf = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);
        mEditor = mPerf.edit();

        writePref();
        readPref();
    }


    private void writePref() {
        mEditor.putString(SP_NAME, "Jim");
        mEditor.putInt(SP_AGE, 25);
        mEditor.putBoolean(SP_SINGLE, false);
        mEditor.commit();

    }

    private void readPref() {
        String name = mPerf.getString(SP_NAME, null);
        int age = mPerf.getInt(SP_AGE, 1);
        boolean single = mPerf.getBoolean(SP_SINGLE, false);
        Log.d(TAG, name + " " + age + " " + single);
    }
}
