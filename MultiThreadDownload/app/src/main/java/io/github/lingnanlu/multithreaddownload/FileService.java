package io.github.lingnanlu.multithreaddownload;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/21.
 */
public class FileService {

    private DBOpenHelper openHelper;

    public FileService(Context context) {
        this.openHelper = new DBOpenHelper(context);
    }

:name=
    public void update(String downloadUrl, int threadId, int pos) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("update filedownlog set downlength=? where downpath=? and threadId=?",
                new Object[] {pos, downloadUrl, threadId});
        db.close();
    }

    public Map<Integer, Integer> getData(String downloadUrl) {

        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select threadId, downlength from filedownlog where " +
                "downpath=?", new String[]{downloadUrl});

        Map<Integer, Integer> data = new HashMap<>();
        while(cursor.moveToNext()) {
            data.put(cursor.getInt(0), cursor.getInt(1));
        }

        cursor.close();
        db.close();

        return data;
    }

    public void delete(String downloadUrl) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("delete from filedownlog where downpath=?", new Object[] {downloadUrl});
        db.close();
    }

    public void save(String downloadUrl, Map<Integer, Integer> data) {

        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                db.execSQL("insert into filedownlog(downpath, threadId, downlength) " +
                        "values(?,?,?)", new Object[] {downloadUrl, entry.getKey(), entry.getValue()});

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();
    }
}
