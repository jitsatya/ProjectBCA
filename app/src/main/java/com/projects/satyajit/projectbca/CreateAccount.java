package com.projects.satyajit.projectbca;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CreateAccount extends AppCompatActivity {
        private Button signUpButton;
        private EditText firstName, lastName, email, userName, password, cnfPassword;
        private DatabaseHelper myDb;
        private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        myDb = new DatabaseHelper(this);
        signUpButton = findViewById(R.id.signup_signup_btn);
        firstName = findViewById(R.id.signup_first_name_et);
        lastName = findViewById(R.id.signup_last_name_et);
        email = findViewById(R.id.signup_email_et);
        //userName = findViewById(R.id.signup_username_et);
        password = findViewById(R.id.signup_password_et);
        //
        // cnfPassword = findViewById(R.id.signup_confirm_password_et);
        mAuth = FirebaseAuth.getInstance();
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
        //String mUserName = userName.getText().toString();
       // String mPassword = password.getText().toString();
        //String mCnfPassword = cnfPassword.getText().toString();

        if (isEmpty())return;
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = authResult.getUser();
                        String UserId = user.getUid();
                        Toast.makeText(CreateAccount.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        UserData userData = new UserData();
                        userData.setEmail(email.getText().toString());
                        userData.setfName(firstName.getText().toString());
                        userData.setlName(lastName.getText().toString());
                        new FirebaseDatabaseHelper("UserData").addUserData(UserId, userData, new FirebaseDatabaseHelper.UserDataStatus() {
                            @Override
                            public void DataIsLoaded(List<UserData> foods, List<String> keys) {
                                Toast.makeText(getBaseContext(), "details added to database", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }
                        });
                        finish();
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FragmentProfile()).commit();*/
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //InProgress(false);
                //Toast.makeText(CreateAccount.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean isEmpty(){
        if (TextUtils.isEmpty(email.getText().toString()))
        {
            email.setError("Required!");
            return true;
        }
        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Required!");
            return true;
        }
        return false;
    }
}
