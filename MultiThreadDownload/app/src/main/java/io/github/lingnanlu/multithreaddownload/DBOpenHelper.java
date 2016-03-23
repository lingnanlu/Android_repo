package io.github.lingnanlu.multithreaddownload;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/3/21.
 */
public class DBOpenHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "eric.db";
    public static final int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog " +
                "(id integer primary key autoincrement," +
                "downpath varchar(100), threadId INTEGER, " +
                "downlength INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS filedownlog");
        onCreate(db);

    }
}
