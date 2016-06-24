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
            sp.edit().putString("user_id",String.valueOf(user_id)).apply();
        }
        else{
            sp.edit().putString("user_id","-1").apply();
        }
    }
    public String getUserId()
    {
        String user_id=sp.getString("user_id",null);

        if(TextUtils.isEmpty(user_id) )
            return null;
        return user_id;

    }

    public void saveSessionTime(int session_time)
    {
        if(session_time>0){
            sp.edit().putString("session_time",String.valueOf(session_time)).apply();
        }
        else{
            sp.edit().putString("session_time","0").apply();
        }
    }
    public String getSessionTime()
    {
        String session_time=sp.getString("session_time",null);

        if(TextUtils.isEmpty(session_time) )
            return "0";
        return session_time;

    }



}
