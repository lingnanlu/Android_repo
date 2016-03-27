package io.github.lingnanlu.datastoredemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/3/23.
 */

public class FileDemo extends AppCompatActivity {

    private static final String TAG = "FileDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        testOpenFile();
        testTempFile();
        testDeleteFile();
    }

    private void testOpenFile() {

        String filename = "myfile";
        String string = "Hello Worls";

        FileOutputStream out = null;
        try {
            out = openFileOutput(filename, Context.MODE_PRIVATE);
            out.write(string.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void testTempFile() {
        String filename = "mytemp";
        String string = "temperory file!";
        FileOutputStream out = null;

        try {
            File file = File.createTempFile(filename, null, getCacheDir());
            out = new FileOutputStream(file);
            out.write(string.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void testDeleteFile() {
        File file = new File("myfile");
        file.delete();
        Log.d(TAG, "file: " + file.getName() + " is deleted");
    }


}
