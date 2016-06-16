package modulo4.ddam.markmota.tk.ejercicio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import modulo4.ddam.markmota.tk.ejercicio1.framgent.FragmentProfile;

/**
 * Created by markmota on 6/15/16.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public String userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflo la vista
        setContentView(R.layout.activity_home);
        // Obtengo la info enviada por el intent
        userName=getIntent().getExtras().getString("key_user");

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

    }


}
