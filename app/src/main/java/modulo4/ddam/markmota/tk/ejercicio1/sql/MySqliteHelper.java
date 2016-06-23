package modulo4.ddam.markmota.tk.ejercicio1.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by markmota on 6/22/16.
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    private final static String LOG_TAG ="SQLiteHelper_log";

    private final static String DATABASE_NAME ="exercise1database";
    private final static int DATABASE_VERSION=1;
    public static final String USER_TABLE_NAME ="users_table";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String USER_COLUMN_USER = "user";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_LAST_LOG = "last_log";

    private static final String USER_CREATE_TABLE ="create table "+USER_TABLE_NAME+
            "("+COLUMN_ID+" integer primary key autoincrement,"+
            USER_COLUMN_USER+" text not null,"+
            USER_COLUMN_PASSWORD+ " text not null,"+
            USER_COLUMN_LAST_LOG+" integer null)";

    private static final String USER_DROP_TABLE ="drop table "+USER_TABLE_NAME;


    public static final String ITEM_TABLE_NAME ="item_table";

    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_DESC = "description";
    public static final String ITEM_COLUMN_RESOURCE = "resource_id";

    private static final String ITEM_CREATE_TABLE ="create table "+ITEM_TABLE_NAME+
            "("+COLUMN_ID+" integer primary key autoincrement,"+
            ITEM_COLUMN_NAME+" text not null,"+
            ITEM_COLUMN_DESC+ " text not null,"+
            ITEM_COLUMN_RESOURCE+" integer not null)";

    private static final String ITEM_DROP_TABLE ="drop table "+USER_TABLE_NAME;

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(USER_CREATE_TABLE);
            db.execSQL(ITEM_CREATE_TABLE);
            Log.d(LOG_TAG,"Success  creating tables for the first time");
        }catch (SQLException e){
            Log.d(LOG_TAG,"Error creating tables for the first time : " + e);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // We are tasting, so we can drop and create all the tables in the database, losing all the data stored.
        // This has to change if we have distributed versions of the app
        try {
            db.execSQL(ITEM_DROP_TABLE);
            db.execSQL(ITEM_CREATE_TABLE);
            db.execSQL(USER_DROP_TABLE);
            db.execSQL(USER_CREATE_TABLE);
            Log.d(LOG_TAG,"Success drooping and creating tables");
        }catch (SQLException e ){
            Log.d(LOG_TAG,"Error drooping or creating tables : " + e);
        }

    }
}
