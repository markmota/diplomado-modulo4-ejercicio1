package modulo4.ddam.markmota.tk.ejercicio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by markmota on 6/15/16.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflo la vista
        setContentView(R.layout.activity_home);
        // Obtengo la info enviada por el intent
        String userName=getIntent().getExtras().getString("key_user");

        // Agrego listeners de click a los botones
        findViewById(R.id.btnFragmentA).setOnClickListener(this);
        findViewById(R.id.btnFragmentB).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnFragmentA:
                changeFragmentA();
                break;
            case R.id.btnFragmentB:
                changeFragmentB();
                break;
        }
    }
    // Implementa la funcionalidad del Fragmento del Perfil
    private void changeFragmentA() {

    }
    // Implementa la funcionalidad del Fragmento de la Lista
    private void changeFragmentB() {

    }


}
