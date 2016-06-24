package modulo4.ddam.markmota.tk.ejercicio1;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import modulo4.ddam.markmota.tk.ejercicio1.model.ModelUser;
import modulo4.ddam.markmota.tk.ejercicio1.sql.UserDataSource;

public class RegisterActivity extends AppCompatActivity {
    private UserDataSource userDataSource;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDataSource = new UserDataSource(getApplicationContext());

        setContentView(R.layout.activity_register);
        final EditText mUser = (EditText) findViewById(R.id.activity_register_input_user);
        final EditText mPassword = (EditText) findViewById(R.id.activity_register_input_password);
        final Button mButton= (Button) findViewById(R.id.activity_register_btn_register);

        if(mButton!=null)
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserName = mUser.getText().toString();
                String password= mPassword.getText().toString();

                // checking empty fields
                if(TextUtils.isEmpty(mUserName)){
                    showMessage(R.string.activity_main_message_empty_user);
                }

                else if(TextUtils.isEmpty(password)){
                    showMessage(R.string.activity_main_message_empty_pass);
                }

                else{
                    //If user and password are not empty, we add the register to the database
                    // Map data in model User
                    ModelUser modelUser= new ModelUser(mUserName,password);
                    userDataSource.saveItem(modelUser);
                    finish();
                }






            }
        });
    }
    private void showMessage(int resourceString) {
        Toast.makeText(getApplicationContext(),resourceString,Toast.LENGTH_SHORT).show();
    }
}
