package io.github.lingnanlu.asynctaskdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);
        new MyAsyncTask().execute();
    }


    private class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            mDialog.show();
            Log.d(TAG, "onPreExecute: " + Thread.currentThread().getName());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setProgress(values[0]);
            Log.d(TAG, "onProgressUpdate: " + Thread.currentThread().getName());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mDialog.dismiss();
            Log.d(TAG, "onPostExecute: " + Thread.currentThread().getName());
        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
            return null;
        }
    }
}
