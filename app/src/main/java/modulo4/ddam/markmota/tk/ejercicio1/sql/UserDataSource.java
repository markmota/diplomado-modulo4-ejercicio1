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
    public boolean saveItem(ModelUser modelItem)
    {
        String username=modelItem.username;
        String password=modelItem.password;
        // Fist we search if the user already exist on the database
        String[] fields_to_recover = new String[] {MySqliteHelper.COLUMN_ID};
        String[] args = new String[] {username};
        String where= MySqliteHelper.USER_COLUMN_USER+"=? ";
        Cursor cursor =db.query(MySqliteHelper.USER_TABLE_NAME,fields_to_recover,where,args,null,null,null);
        if (!cursor.moveToNext())
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MySqliteHelper.USER_COLUMN_USER,username);
            contentValues.put(MySqliteHelper.USER_COLUMN_PASSWORD,password);
            db.insert(MySqliteHelper.USER_TABLE_NAME,null,contentValues);
            return true;
        }
        return false;


    }


    public ModelUser checkLog(String username, String password)
    {
        String[] fields_to_recover = new String[] {MySqliteHelper.COLUMN_ID,MySqliteHelper.USER_COLUMN_LAST_LOG};
        String[] args = new String[] {username,password};
        String where= MySqliteHelper.USER_COLUMN_USER+"=? AND "+
                MySqliteHelper.USER_COLUMN_PASSWORD+"=?";
        Cursor cursor =db.query(MySqliteHelper.USER_TABLE_NAME,fields_to_recover,where,args,null,null,null);
        if (cursor.moveToNext())
        {
            String last_log = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.USER_COLUMN_LAST_LOG));
            int user_id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ID));


            // update las log on database
            updateLastLog(username);
            // return data

            return new ModelUser(user_id,last_log);
        }
        else{
            return  null;
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
    public ModelUser getUser(int id_user)
    {
        ModelUser modelUser;

        String[] fields_to_recover = new String[] {MySqliteHelper.USER_COLUMN_USER,MySqliteHelper.USER_COLUMN_PASSWORD};
        String[] args = new String[] {String.valueOf(id_user)};
        String where= MySqliteHelper.COLUMN_ID+"=? ";
        Cursor cursor =db.query(MySqliteHelper.USER_TABLE_NAME,fields_to_recover,where,args,null,null,null);
        if (cursor.moveToNext())
        {

            String user=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.USER_COLUMN_USER));
            String password=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.USER_COLUMN_PASSWORD));

            modelUser = new ModelUser(id_user,user,password);
            return  modelUser;
        }
        else{
            return null;
        }

    }
}
