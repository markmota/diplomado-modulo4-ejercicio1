package modulo4.ddam.markmota.tk.ejercicio1.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modulo4.ddam.markmota.tk.ejercicio1.model.ModelUser;

/**
 * Created by markmota on 6/23/16.
 */
public class UserDataSource {
    private final SQLiteDatabase db;

    public UserDataSource(Context context) {
        MySqliteHelper helper = new MySqliteHelper(context);
        db=helper.getWritableDatabase();
    }
    public void saveItem(ModelUser modelItem)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.USER_COLUMN_USER,modelItem.username);
        contentValues.put(MySqliteHelper.USER_COLUMN_PASSWORD,modelItem.password);
        db.insert(MySqliteHelper.USER_TABLE_NAME,null,contentValues);
    }


    public int checkLog(String username, String password)
    {
        String[] fields_to_recover = new String[] {MySqliteHelper.USER_COLUMN_LAST_LOG};
        String[] args = new String[] {username,password};
        String where= MySqliteHelper.USER_COLUMN_USER+"=? AND "+
                MySqliteHelper.USER_COLUMN_PASSWORD+"=?";
        Cursor cursor =db.query(MySqliteHelper.USER_TABLE_NAME,fields_to_recover,where,args,null,null,null);
        if (cursor.moveToNext())
        {
            int last_log = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.USER_COLUMN_LAST_LOG));
            updateLastLog(username);
            return last_log;
        }
        else{
            return  0;
        }
    }
    private void updateLastLog(String username){
        // Getting current complete date and time
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        ContentValues fields_to_update = new ContentValues();
        fields_to_update.put(MySqliteHelper.USER_COLUMN_LAST_LOG,String.valueOf(timestamp));

        String[] args = new String[] {username};
        String where= MySqliteHelper.USER_COLUMN_USER+"=? ";
        db.update(MySqliteHelper.USER_TABLE_NAME,fields_to_update,where,args);

    }
}
