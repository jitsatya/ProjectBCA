package com.projects.satyajit.projectbca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
        private Button signUpButton;
        private EditText firstName, lastName, email, userName, password, cnfPassword;
        private DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        myDb = new DatabaseHelper(this);
        signUpButton = findViewById(R.id.signup_signup_btn);
        firstName = findViewById(R.id.signup_first_name_et);
        lastName = findViewById(R.id.signup_last_name_et);
        email = findViewById(R.id.signup_email_et);
        userName = findViewById(R.id.signup_username_et);
        password = findViewById(R.id.signup_password_et);
        cnfPassword = findViewById(R.id.signup_confirm_password_et);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    private void register(){
        String mFirstName = firstName.getText().toString();
        String mLastName = lastName.getText().toString();
        String mEmail = email.getText().toString();
        String mUserName = userName.getText().toString();
        String mPassword = password.getText().toString();
        String mCnfPassword = cnfPassword.getText().toString();

        if (mUserName.isEmpty()){
            Toast.makeText(this, "Please enter Data in all the Fields", Toast.LENGTH_SHORT).show();
        }
        else{
            myDb.insertUserData(mFirstName,mLastName,mEmail,mUserName,mPassword);
            Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
