package io.github.lingnanlu.singlethreaddownloader;


import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/21.
 */
public class FileDownloader {

    private static final String TAG = "FileDownloader";
    private OnProgressUpdateListener progressUpdateListener;
    private InternalHandler handler = new InternalHandler();

    private String fileUrl;

    public FileDownloader(String fileUrl, OnProgressUpdateListener progressUpdateListener) {
        this.progressUpdateListener = progressUpdateListener;
        this.fileUrl = fileUrl;
    }

    public FileDownloader(String fileUrl) {
        this(fileUrl, null);
    }

    public void download() {
        new Thread(){

            @Override
            public void run() {

                InputStream in = null;
                FileOutputStream out = null;
                try {
                    URL url = new URL(fileUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    Log.d(TAG, conn.getRequestMethod());
                    conn.connect();
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        in = conn.getInputStream();
                        byte[] buffer = new byte[1024];
                        File saveDir = Environment.getExternalStorageDirectory();
                        if(!saveDir.exists()) {
                            saveDir.mkdirs();
                        }
                        File saveFile = new File(saveDir, "hehe.mp3");
                        out = new FileOutputStream(saveFile);
                        int count = 0;

                        while((in.read(buffer) != -1)) {
                            out.write(buffer);
                            count += 10;
                            Message msg = handler.obtainMessage(count);
                            msg.sendToTarget();

                        }

                    }

                    out.close();
                    in.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    private class InternalHandler extends Handler {

        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            progressUpdateListener.onProgressUpdate(msg.what);
        }
    }
}
