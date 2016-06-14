package modulo4.ddam.markmota.tk.ejercicio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser=(EditText) findViewById(R.id.activity_main_input_user);
        etPass=(EditText) findViewById(R.id.activity_main_input_password);
        findViewById(R.id.activity_main_btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_main_btn_login:
                checkValues();
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
        if(user.equals("unam") && pass.equals("curso"))
        {
            showMessage(R.string.activity_main_message_success_login);
            Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
            //Intent intent= new Intent(getApplicationContext(),ActivityDetail.class);
            //intent.putExtra("key_user",usuario);
            //startActivity(intent);
        }
        else {
            // Limpiamos la contraseña de los editText
            etPass.setText("");
            showMessage(R.string.activity_main_message_error_login);
        }
    }

    private void showMessage(int resourceString) {
        Toast.makeText(getApplicationContext(),resourceString,Toast.LENGTH_SHORT).show();
    }

}
