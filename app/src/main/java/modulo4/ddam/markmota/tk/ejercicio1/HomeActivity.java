package modulo4.ddam.markmota.tk.ejercicio1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import modulo4.ddam.markmota.tk.ejercicio1.framgent.FragmentList;
import modulo4.ddam.markmota.tk.ejercicio1.framgent.FragmentProfile;
import modulo4.ddam.markmota.tk.ejercicio1.service.ServiceTimer;

/**
 * Created by markmota on 6/15/16.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public String userName;
    private int sessionTimeSaved;
    private TextView sessionTime;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int counter = intent.getExtras().getInt("timer");
            sessionTime.setText(String.format(getString(R.string.activity_home_message_counter),counter));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflo la vista
        setContentView(R.layout.activity_home);
        sessionTime= (TextView) findViewById(R.id.activity_home_session_time);
        // Obtengo la info enviada por el intent
        userName=getIntent().getExtras().getString("user_name");




        // Agrego listeners de click a los botones
        findViewById(R.id.activity_home_btnFragmentA).setOnClickListener(this);
        findViewById(R.id.activity_home_btnFragmentB).setOnClickListener(this);
        findViewById(R.id.activity_home_btnBack).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_home_btnFragmentA:
                changeFragmentA();
                break;
            case R.id.activity_home_btnFragmentB:
                changeFragmentB();
                break;
            case R.id.activity_home_btnBack:
                finish();
                break;
        }
    }
    // Implementa la funcionalidad del Fragmento del Perfil
    private void changeFragmentA() {
        FragmentProfile f = FragmentProfile.newInstance(userName);
        getFragmentManager().beginTransaction().replace(R.id.activity_home_fragmentHolder,f).commit();
    }
    // Implementa la funcionalidad del Fragmento de la Lista
    private void changeFragmentB() {
        getFragmentManager().beginTransaction().replace(R.id.activity_home_fragmentHolder,new FragmentList()).commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceTimer.ACTION_SEND_TIMER);
        registerReceiver(broadcastReceiver,filter);
        Log.d(ServiceTimer.TAG,"OnResume, se reinicia boradcast");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ServiceTimer.TAG,"onPause quitando broadcast");
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ServiceTimer.TAG,"OnDestroy, terminando servicio");

        stopService(new Intent(getApplicationContext(),ServiceTimer.class));

    }


}
