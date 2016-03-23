package io.github.lingnanlu.multithreaddownload;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/21.
 */
public class DownloadThread extends Thread{


    private static final String TAG = "DownloadThread";
    private File saveFile;
    private URL downUrl;
    private int block;

    private int threadId = -1;
    private int downLength;
    private boolean finish = false;
    private FileDownloader downloader;


    public DownloadThread(FileDownloader fileDownloader, URL url, File saveFile, int
            blockOfEverythread, Integer downLength, int threadId) {
        this.downUrl = url;
        this.saveFile = saveFile;
        this.block = blockOfEverythread;
        this.downloader = fileDownloader;
        this.threadId = threadId;
        this.downLength = downLength;

    }

    public boolean isFinish() {

        return finish;

    }

    public static void print(String msg) {
        Log.i(TAG, msg);
    }
    @Override
    public void run() {
        if (downLength < block) {
            try {
                HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
                http.setConnectTimeout(5 * 1000);
                http.setRequestMethod("GET");

                int startPos = block * (threadId - 1) + downLength;
                int endPos = block * threadId - 1;
                http.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                http.setRequestProperty("Connection", "Keep-Alive");

                InputStream in = http.getInputStream();
                byte[] buffer = new byte[1024];
                int offset = 0;
                print("Thread " + this.threadId + " start download from position " + startPos);

                RandomAccessFile threadfile = new RandomAccessFile(this.saveFile, "rwd");

                threadfile.seek(startPos);
                while(!downloader.getExit() && (offset = in.read(buffer, 0, 1024)) != -1){
                    threadfile.write(buffer, 0, offset);
                    downLength += offset;
                    downloader.update(this.threadId, downLength);
                    downloader.append(offset);
                }

                threadfile.close();
                in.close();
                print("Thread " + this.threadId + " download finish");
                this.finish = true;

            } catch (IOException e) {
                this.downLength = -1;
                print("Thread " + this.threadId + ":" + e);
            }
        }
    }

    public long getDownLength() {
        return downLength;
    }
}
