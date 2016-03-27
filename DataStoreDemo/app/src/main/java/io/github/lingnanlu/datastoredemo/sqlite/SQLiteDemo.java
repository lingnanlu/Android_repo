package io.github.lingnanlu.datastoredemo.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.github.lingnanlu.datastoredemo.R;

/**
 * Created by Administrator on 2016/3/23.
 */
public class SQLiteDemo extends AppCompatActivity {

    private static final String TAG = "SQLiteDemo";
    private FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        openDatabase();
        testWriteDatabase(1, "first", "unknow test");
        testWriteDatabase(2, "second", "hello kitty");
        testReadDatabase();
//
//        testUpdateDatabase();
//        testReadDatabase();
    }

    private void openDatabase() {
        Log.d(TAG, "openDatabase");
        mDbHelper = new FeedReaderDbHelper(getApplicationContext());


    }

    private void testWriteDatabase(int id, String title, String content) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_ENTRY_ID, id);
        values.put(FeedReaderContract.FeedEntry.COLUMN_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_CONTENT, content);

        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NULLABLE,
                values);

    }

    private void testReadDatabase() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_ENTRY_ID,
                FeedReaderContract.FeedEntry.COLUMN_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_CONTENT,

        };

        String selection = FeedReaderContract.FeedEntry.COLUMN_ENTRY_ID + "=?";
        String[] selectionArgs = new String[]{"1"};

        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Log.d(TAG, "testReadDatabase");

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(FeedReaderContract.FeedEntry._ID));
            int entryID = c.getInt(c.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_ENTRY_ID));
            String title = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry
                    .COLUMN_TITLE));
            String content = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry
                    .COLUMN_CONTENT));

            Log.d(TAG, "id = " + id + " entryid = " + entryID + " title = " + title + " content =" +
                    " " + content);
        }
    }

    private void testDeleteDatabase() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = FeedReaderContract.FeedEntry.COLUMN_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { "1" };

        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);

    }

    private void testUpdateDatabase() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_TITLE, "text updated!");

        String selection = FeedReaderContract.FeedEntry.COLUMN_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {"1"};
        int count = db.update(FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }
}

public class Person implements Parcelable{
    private String name;
    private int age;
    private static final String TAG = ParcelableTest.TAG;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public static final Parcelable.Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            Log.d(TAG,"createFromParcel");
            Person mPerson = new Person();
            mPerson.name = source.readString();
            mPerson.age = source.readInt();
            return mPerson;
        }
        @Override
        public Person[] newArray(int size) {
// TODO Auto-generated method stub
            return new Person[size];
        }
    };
    @Override
    public int describeContents() {
// TODO Auto-generated method stub
        Log.d(TAG,"describeContents");
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
// TODO Auto-generated method stub
        Log.d(TAG,"writeToParcel");
        dest.writeString(name);
        dest.writeInt(age);
    }
}
