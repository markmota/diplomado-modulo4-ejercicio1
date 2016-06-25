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
import modulo4.ddam.markmota.tk.ejercicio1.util.PreferenceUtil;

/**
 * Created by markmota on 6/15/16.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public String userName;
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
        setContentView(R.layout.activity_home);

        // Getting the info sended from the precious activity
        userName=getIntent().getExtras().getString("user_name");

        // Linking layout elements with objects
        sessionTime= (TextView) findViewById(R.id.activity_home_session_time);

        // Setting click listener to buttons
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
                eraseSession();
                break;
        }
    }
    // Implements profile fragment functionality
    private void changeFragmentA() {
        FragmentProfile f = FragmentProfile.newInstance(userName);
        getFragmentManager().beginTransaction().replace(R.id.activity_home_fragmentHolder,f).commit();
    }
    // Implements list fragment functionality
    private void changeFragmentB() {
        getFragmentManager().beginTransaction().replace(R.id.activity_home_fragmentHolder,new FragmentList()).commit();
    }
    private void eraseSession() {
        // Erasing preferences
        PreferenceUtil preferenceUtil= new PreferenceUtil(getApplicationContext());
        preferenceUtil.saveUserId(0);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceTimer.ACTION_SEND_TIMER);
        registerReceiver(broadcastReceiver,filter);
        Log.d(ServiceTimer.TAG,"OnResume, broadcast restarted");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ServiceTimer.TAG,"onPause quiting broadcast");
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ServiceTimer.TAG,"OnDestroy, ending service");
        // Stop service counter
        stopService(new Intent(getApplicationContext(),ServiceTimer.class));

    }


}
