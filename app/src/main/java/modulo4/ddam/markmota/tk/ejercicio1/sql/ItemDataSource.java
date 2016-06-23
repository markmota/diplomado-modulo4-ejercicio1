package modulo4.ddam.markmota.tk.ejercicio1.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import modulo4.ddam.markmota.tk.ejercicio1.model.ModelItem;

/**
 * Created by markmota on 6/23/16.
 */
public class ItemDataSource {

    private final SQLiteDatabase db;

    public ItemDataSource(Context context) {
        MySqliteHelper helper = new MySqliteHelper(context);
        db=helper.getWritableDatabase();
    }
    public void saveItem(ModelItem modelItem)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHelper.ITEM_COLUMN_NAME,modelItem.item);
        contentValues.put(MySqliteHelper.ITEM_COLUMN_DESC,modelItem.description);
        contentValues.put(MySqliteHelper.ITEM_COLUMN_RESOURCE,modelItem.resourceId);
        db.insert(MySqliteHelper.ITEM_TABLE_NAME,null,contentValues);
    }
    public void deleteItem(ModelItem modelItem)
    {
        db.delete(MySqliteHelper.ITEM_TABLE_NAME,MySqliteHelper.COLUMN_ID+"=?",
                new String[]{String.valueOf(modelItem.id)});
    }

    public List<ModelItem> getAllItems()
    {
        List<ModelItem> modelItemList = new ArrayList<>();
        Cursor cursor =db.query(MySqliteHelper.ITEM_TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ID));
            String itemName=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.ITEM_COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.ITEM_COLUMN_DESC));
            int resourceId = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.ITEM_COLUMN_RESOURCE));
            ModelItem modelItem = new ModelItem(id,itemName,description,resourceId);
            modelItemList.add(modelItem);
        }

        return modelItemList;
    }
}
