package modulo4.ddam.markmota.tk.ejercicio1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import modulo4.ddam.markmota.tk.ejercicio1.util.PreferenceUtil;

/**
 * Created by markmota on 6/24/16.
 */
public class ServiceTimer  extends Service{
    public static final String TAG = "timer_tag";
    public static final String ACTION_SEND_TIMER ="modulo4.ddam.markmota.tk.ejercicio1.SEND_TIMER";
    public int counter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            counter++;
            handler.postDelayed(runnable,1000);
            Intent i = new Intent(ACTION_SEND_TIMER);
            i.putExtra("timer",counter);
            sendBroadcast(i);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // getting the extras  to start the counter with the data saved in the preferences
        counter=intent.getExtras().getInt("initial_counter_value");
        Log.d(TAG,"OnstartCommand called");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Oncreate servicio");
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"OnDestroy Servicio");
        PreferenceUtil preferenceUtil;
        // Saving the service counter value in the preferences
        preferenceUtil= new PreferenceUtil(getApplicationContext());
        preferenceUtil.saveSessionTime(counter);
        handler.removeCallbacks(runnable);
    }
}
