package modulo4.ddam.markmota.tk.ejercicio1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import modulo4.ddam.markmota.tk.ejercicio1.model.ModelUser;

/**
 * Created by markmota on 6/24/16.
 */
public class PreferenceUtil {
    private static final String FILE_NAME ="system_pref";
    private final SharedPreferences sp;

    public PreferenceUtil(Context context)
    {
        sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
    }
    public void saveUserId(int user_id)
    {
        if(user_id>0){
            sp.edit().putInt("user_id",user_id).apply();
        }
        else{
            sp.edit().putInt("user_id",0).apply();
        }
    }
    public int getUserId()
    {
         int user_id=sp.getInt("user_id",0);

        return user_id;

    }

    public void saveSessionTime(int session_time)
    {
        if(session_time!=0){
            sp.edit().putInt("session_time",session_time).apply();
        }
        else{
            sp.edit().putInt("session_time",0).apply();
        }
    }
    public int getSessionTime()
    {
        int session_time=sp.getInt("session_time",0);
        return session_time;

    }




}
