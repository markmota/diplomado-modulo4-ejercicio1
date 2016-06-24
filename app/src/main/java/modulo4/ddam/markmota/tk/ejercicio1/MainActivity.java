package modulo4.ddam.markmota.tk.ejercicio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

//import java.util.HashMap;

import modulo4.ddam.markmota.tk.ejercicio1.service.ServiceTimer;
import modulo4.ddam.markmota.tk.ejercicio1.sql.UserDataSource;
import modulo4.ddam.markmota.tk.ejercicio1.util.PreferenceUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etPass;
    private CheckBox rememberCheck;
    //private HashMap<String, String> dataUsers = new HashMap<String, String>();
    private UserDataSource userDataSource;
    private PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceUtil= new PreferenceUtil(getApplicationContext());
        userDataSource= new UserDataSource(getApplicationContext());
        setContentView(R.layout.activity_main);
        etUser=(EditText) findViewById(R.id.activity_main_input_user);
        etPass=(EditText) findViewById(R.id.activity_main_input_password);
        rememberCheck= (CheckBox) findViewById(R.id.activity_main_check_remember);
        findViewById(R.id.activity_main_btn_login).setOnClickListener(this);
        findViewById(R.id.activity_main_btn_register).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_main_btn_login:
                checkValues();
                break;
            case R.id.activity_main_btn_register:
                addUser();
                break;
        }
    }



    private void checkValues() {
        final String user= etUser.getText().toString();
        final String pass= etPass.getText().toString();

        // Verificamos que los campos no esten vacíos

        if(TextUtils.isEmpty(user))
            showMessage(R.string.activity_main_message_empty_user);
        else if(TextUtils.isEmpty(pass))
            showMessage(R.string.activity_main_message_empty_pass);
        else   //Al no estar vacia comenzamos el proceso de validacion
            validateLogin(user,pass);

    }

    private void validateLogin(String user, String pass) {

        // Obtengo los usuarios disponibles
        //getUsers();


        /*
        // Verifico que existan
        if( dataUsers.get(user)!=null &&  pass.equals(dataUsers.get(user)))
        {
            showMessage(R.string.activity_main_message_success_login);
            // Limpio textos de entrada
            etPass.setText("");
            etUser.setText("");

            Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("key_user",user);
            startActivity(intent);
        }
        */

        // Establish connexion to the database and check the user and password
        int[] user_data=userDataSource.checkLog(user,pass);
        if(user_data[0]==-1){
            showMessage(R.string.activity_main_message_success_login);
            // Limpio textos de entrada
            etPass.setText("");
            etUser.setText("");

            // Check if the check box remember user is on, i have to remember the user id
            if(rememberCheck.isChecked()){
                preferenceUtil.saveUserId(user_data[0]);
            }
            else{
                preferenceUtil.saveUserId(-1);
            }

            // Now I get the session time form the preference util
            String session_time=preferenceUtil.getSessionTime();

            Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("user_name",user);
            intent.putExtra("session_time",session_time);
            startActivity(intent);

            Intent service=new Intent(getApplicationContext(), ServiceTimer.class);
            service.putExtra("initial_counter_value",session_time);
            startService(service);

        }
        else {
            // Limpiamos la contraseña de los editText
            etPass.setText("");
            showMessage(R.string.activity_main_message_error_login);
        }
    }
    // Send to the register activity
    private void addUser() {
        Intent intent= new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
    }

    private void showMessage(int resourceString) {
        Toast.makeText(getApplicationContext(),resourceString,Toast.LENGTH_SHORT).show();
    }
    /*  This is not used any more, because we save the users in a database, but I leave it here for reference proposal
    private void getUsers() {
        // Por el momento uso un hashmap para guardar los datos de usuarios
        dataUsers.put("Marduk","marduk");
        dataUsers.put("Esteban","esteban");
        dataUsers.put("Aurelio","aurelio");
        dataUsers.put("Laura","laura");
        dataUsers.put("Nico","nico");
        dataUsers.put("Ricardo","ricardo");
        dataUsers.put("Sandra","sandra");
        dataUsers.put("Zafiro","zafiro");

    }*/

}
