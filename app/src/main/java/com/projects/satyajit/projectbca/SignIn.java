package com.projects.satyajit.projectbca;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText email;
    private EditText password;

    private Button signIn;
    private Button register;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.signin_email_et);
        password = findViewById(R.id.signin_password_et);
        signIn = findViewById(R.id.signin_btn);
        register = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progressBar);
        InProgress(false);

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEmpty())return;
                    InProgress(true);
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(SignIn.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            InProgress(false);
                            Toast.makeText(SignIn.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignIn.this, CreateAccount.class);
                    startActivity(intent);
                }
            });

    }

    private void InProgress(boolean x){
            if (x)
            {
                progressBar.setVisibility(View.VISIBLE);
                signIn.setEnabled(false);
                register.setEnabled(false);
            }else {
                progressBar.setVisibility(View.GONE);
                signIn.setEnabled(true);
                register.setEnabled(true);
            }
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
