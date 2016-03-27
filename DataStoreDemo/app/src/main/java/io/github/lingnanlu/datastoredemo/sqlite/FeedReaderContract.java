package io.github.lingnanlu.datastoredemo.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/3/23.
 * 一个数据库对应一个类
 */
public final class FeedReaderContract {

    private FeedReaderContract(){}

    /*
     * 数据库中的一个表对应一个该类
     *
     * By implementing the BaseColumns interface,
     * your inner class can inherit a primary key field called _ID
     * that some Android classes such as cursor adaptors will expect it to have.
     * It's not required, but this can help your database work harmoniously
     * with the Android framework.
     */
    public static final class FeedEntry implements BaseColumns {

        //该Table的描述
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_ENTRY_ID = "entryid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_NULLABLE = "";


        //关于该Table的操作, 一定要注意空格, 否则很容易建表错误
        public static final String SQL_CREATE_ENTRIES = "create table " + TABLE_NAME + " ("
                + _ID + " integer primary key, "
                + COLUMN_ENTRY_ID + " text, "
                + COLUMN_TITLE + " text,"
                + COLUMN_CONTENT + " text"
                + ")";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;


    }
}
