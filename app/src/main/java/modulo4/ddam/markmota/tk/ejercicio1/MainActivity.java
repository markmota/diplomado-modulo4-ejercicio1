package modulo4.ddam.markmota.tk.ejercicio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import modulo4.ddam.markmota.tk.ejercicio1.model.ModelUser;
import modulo4.ddam.markmota.tk.ejercicio1.service.ServiceTimer;
import modulo4.ddam.markmota.tk.ejercicio1.sql.UserDataSource;
import modulo4.ddam.markmota.tk.ejercicio1.util.PreferenceUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etPass;
    private CheckBox rememberCheck;
    private UserDataSource userDataSource;
    private PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceUtil= new PreferenceUtil(getApplicationContext());
        userDataSource= new UserDataSource(getApplicationContext());

        // Linking layout elements with objects
        etUser=(EditText) findViewById(R.id.activity_main_input_user);
        etPass=(EditText) findViewById(R.id.activity_main_input_password);
        rememberCheck= (CheckBox) findViewById(R.id.activity_main_check_remember);

        // Setting click listener to buttons
        findViewById(R.id.activity_main_btn_login).setOnClickListener(this);
        findViewById(R.id.activity_main_btn_register).setOnClickListener(this);

        // checking if we have an user id saved
        checkSavedData();
    }

    private void checkSavedData() {
        // Getting user from preferences if is the option remember on
        // and setting the text in the layout elements
        int user_id= preferenceUtil.getUserId();
        if(user_id!=0){
            ModelUser userDataSaver=userDataSource.getUser(user_id);
            etUser.setText(userDataSaver.username);
            etPass.setText(userDataSaver.password);
            rememberCheck.setChecked(true);
            // Hack to skip the layout log
            validateLogin(userDataSaver.username,userDataSaver.password);
        }
    }

    // This implements the button actions
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
    // Sanitising data
    private void checkValues() {
        // Setting data object from variables
        final String user= etUser.getText().toString();
        final String pass= etPass.getText().toString();

        // check if fields are no empty
        if(TextUtils.isEmpty(user))
            showMessage(R.string.activity_main_message_empty_user);
        else if(TextUtils.isEmpty(pass))
            showMessage(R.string.activity_main_message_empty_pass);
        else   //If is not empty we proceed to validate user

            validateLogin(user,pass);
    }
    // Validating data and starting new activity
    private void validateLogin(String user, String pass) {

        // Establish connexion to the database and check the user and password
        ModelUser user_data=userDataSource.checkLog(user,pass);

        if(user_data != null){
            if(user_data.last_log!=null){
                // Show welcome and last log
                String welcome= String.format(getString(R.string.activity_main_message_success_login),user_data.last_log);
                Toast.makeText(getApplicationContext(),welcome,Toast.LENGTH_LONG).show();
            }
            else{
                String welcome= String.format(getString(R.string.activity_main_message_success_login)," First Time.");
                Toast.makeText(getApplicationContext(),welcome,Toast.LENGTH_LONG).show();
            }



            // Check if the check box remember user is on, if is I have to remember the user id
            if(rememberCheck.isChecked()){
                preferenceUtil.saveUserId(user_data.id);
            }
            else{
                preferenceUtil.saveUserId(0);
            }
            // Now I get the session time form the preference util
            int session_time= preferenceUtil.getSessionTime();
            // Clean layout fields
            etPass.setText("");
            etUser.setText("");
            rememberCheck.setChecked(false);



            // Start to configure the start of the next  activity
            Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
            // Variables to send to the next activity
            intent.putExtra("user_name",user);
            intent.putExtra("last_log",user_data.last_log);
            intent.putExtra("session_time",session_time);

            startActivity(intent);

            // Start service, the session counter
            Intent service=new Intent(getApplicationContext(), ServiceTimer.class);
            // Variables to send to the service
            service.putExtra("initial_counter_value",session_time);
            startService(service);

        }
        else {
            // clean the password in the layout field
            etPass.setText("");
            showMessage(R.string.activity_main_message_error_login);
        }
    }
    // Start activity to the register activity
    private void addUser() {
        Intent intent= new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
    }
    // Showing toast messages
    private void showMessage(int resourceString) {
        Toast.makeText(getApplicationContext(),resourceString,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkSavedData();
    }

}
