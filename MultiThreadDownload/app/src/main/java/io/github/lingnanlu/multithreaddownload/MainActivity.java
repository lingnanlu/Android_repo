package io.github.lingnanlu.multithreaddownload;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    public static final int PROCESSING = 1;
    public static final int FAILURE = -1;

    private Button bt_download;
    private Button bt_pause;
    private ProgressBar pb_download;

    private Handler handler = new UIHandler();
    private final class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROCESSING:
                    pb_download.setProgress(msg.getData().getInt("size"));
                    if (pb_download.getProgress() == pb_download.getMax()) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                    }

                   break;
                case FAILURE:
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_download = (Button) findViewById(R.id.bt_download);
        bt_pause = (Button) findViewById(R.id.bt_pause);

        pb_download = (ProgressBar) findViewById(R.id.pb_download);

        ButtonClickListener listener = new ButtonClickListener();

        bt_download.setOnClickListener(listener);
        bt_pause.setOnClickListener(listener);
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_download:
                    String path = "http://abv.cn/music/光辉岁月.mp3";
                    String filename = path.substring(path.lastIndexOf('/') + 1);

                    try {
                        filename = URLEncoder.encode(filename, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    path = path.substring(0, path.lastIndexOf('/') + 1) + filename;

                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File savDir = Environment.getExternalStorageDirectory();
                        download(path, savDir);
                    } else {
                        Toast.makeText(getApplicationContext(), "sd card error", Toast
                                .LENGTH_LONG).show();
                    }
                    bt_download.setEnabled(false);
                    bt_pause.setEnabled(true);
                    break;
                case R.id.bt_pause:
                    exit();
                    Toast.makeText(getApplicationContext(),
                            "Now thread is Stopping!!", Toast.LENGTH_LONG).show();
                    bt_download.setEnabled(true);
                    bt_pause.setEnabled(false);
                    break;
            }

        }

        private DownloadTask task;

        private void exit() {
            if( task != null)
                task.exit();
        }

        private void download(String path, File savDir) {
            task = new DownloadTask(path, savDir);
            new Thread(task).start();
        }

        private final class DownloadTask implements Runnable {

            private String path;
            private File saveDir;
            private FileDownloader loader;

            public DownloadTask(String path, File saveDir) {
                this.path = path;
                this.saveDir = saveDir;
            }

            public void exit() {
                if ( loader != null) {
                    loader.exit();
                }
            }

            DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
                @Override
                public void onDownloadSize(int downloadSize) {
                    Message msg = new Message();
                    msg.what = PROCESSING;
                    msg.getData().putInt("size", downloadSize);
                    handler.sendMessage(msg);
                }
            };
            @Override
            public void run() {
                try {
                    loader = new FileDownloader(getApplicationContext(), path, saveDir, 3);
                    pb_download.setMax(loader.getFileSize());
                    loader.download(downloadProgressListener);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendMessage(handler.obtainMessage(FAILURE));
                }

            }
        }
    }

}
