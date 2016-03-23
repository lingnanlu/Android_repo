package io.github.lingnanlu.singlethreaddownloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements OnProgressUpdateListener {


    private Button bt_Download;
   // private Button bt_Pause;
    private ProgressBar pb_Progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_Download = (Button) findViewById(R.id.bt_download);
        //bt_Pause = (Button) findViewById(R.id.bt_pause);
        pb_Progress = (ProgressBar) findViewById(R.id.pb_download);
        pb_Progress.setMax(100);
        bt_Download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FileDownloader fileDownloader = new FileDownloader(
                        "http://lingnanlu.github.io/2015/09/18/thinking-in-java-notes", MainActivity.this);
                fileDownloader.download();

            }
        });

    }

    @Override
    public void onProgressUpdate(int downloadSize) {
        pb_Progress.setProgress(downloadSize);
    }
}
