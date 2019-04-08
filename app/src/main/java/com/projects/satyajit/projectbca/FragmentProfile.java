package com.projects.satyajit.projectbca;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FragmentProfile extends Fragment {
    private Button signUpButton, loginButton, logOut;
    private EditText loginUsername, loginPassword;
    private TextView welcomeMessage;
    private LinearLayout loginLayout;
    DatabaseHelper myDb;
    private Session session;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_profile, container, false);
        myDb = new DatabaseHelper(this.getContext());
        session = new Session(this.getContext());
        signUpButton = view.findViewById(R.id.to_sign_up_button);
        loginButton = view.findViewById(R.id.login_login_btn);
        logOut = view.findViewById(R.id.logout_button);
        loginUsername = view.findViewById(R.id.login_username_et);
        loginPassword = view.findViewById(R.id.login_password_et);
        welcomeMessage = view.findViewById(R.id.welcome_message);
        loginLayout = view.findViewById(R.id.login_linear_layout);
        if (session.loggedIn()){
            loginLayout.setVisibility(View.GONE);
            welcomeMessage.setText("Welcome ");
        }
        if (!session.loggedIn()){
            loginLayout.setVisibility(View.VISIBLE);
            logOut.setVisibility(View.INVISIBLE);
            welcomeMessage.setVisibility(View.INVISIBLE);
        }
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateAccount.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                logOut.setVisibility(View.INVISIBLE);
                welcomeMessage.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
    private void login(){
        String mUsername = loginUsername.getText().toString();
        String mPassword = loginPassword.getText().toString();

        if (myDb.verifyUser(mUsername,mPassword)){
            session.setLoggedIn(true);
            loginLayout.setVisibility(View.GONE);
            logOut.setVisibility(View.VISIBLE);
            welcomeMessage.setText("Welcome " +mUsername);
            welcomeMessage.setVisibility(View.VISIBLE);
            Toast.makeText(this.getContext(), "Login Successfull!", Toast.LENGTH_LONG ).show();
        }
        else
        {
            Toast.makeText(this.getContext(), "Wrong Username or Password", Toast.LENGTH_LONG).show();
        }
    }
    private void logout(){
        session.setLoggedIn(false);
        loginLayout.setVisibility(View.VISIBLE);

    }
}
