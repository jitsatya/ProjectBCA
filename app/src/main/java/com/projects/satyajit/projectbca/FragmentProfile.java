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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class FragmentProfile extends Fragment {
    private Button signUpButton, loginButton, logOut, updateProfileButton;
    private EditText loginUsername, loginPassword;
    private TextView usersName, usersEmail, usersAge, usersGender;
    private ImageView usersImage;
    private LinearLayout loginLayout;
    private RelativeLayout profileLayout;
    UserData userData = new UserData();
    DatabaseHelper myDb;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_profile, container, false);
        myDb = new DatabaseHelper(this.getContext());
        signUpButton = view.findViewById(R.id.to_sign_up_button);
        loginButton = view.findViewById(R.id.login_login_btn);
        logOut = view.findViewById(R.id.logout_button);
        updateProfileButton = view.findViewById(R.id.update_profile_button);
        loginLayout = view.findViewById(R.id.login_linear_layout);
        profileLayout = view.findViewById(R.id.profile_relative_layout);
        usersName  = view.findViewById(R.id.users_full_name);
        usersEmail  = view.findViewById(R.id.users_email);
        usersAge  = view.findViewById(R.id.users_age);
        usersGender  = view.findViewById(R.id.users_gender);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            loginLayout.setVisibility(View.GONE);
        }else{
            loginLayout.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(getContext(), SignIn.class);
                startActivity(intent);

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                loginLayout.setVisibility(View.VISIBLE);
                profileLayout.setVisibility(View.GONE);
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateProfile.class);
                startActivity(intent);
            }
        });
        getdata();
        return view;
    }
    private void login(){
        String mUsername = loginUsername.getText().toString();
        String mPassword = loginPassword.getText().toString();


    }
    private void getdata(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserData user = snapshot.getValue(UserData.class);
                        userData = user;
                        usersName.setText(user.getfName() + user.getlName());
                        usersAge.setText("Age: " + user.getAge());
                        usersEmail.setText(user.getEmail());
                        usersGender.setText("Gender: "+ user.getGender());

                    }
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Query query6 = FirebaseDatabase.getInstance().getReference("UserData")
                .equalTo(mAuth.getUid());
        query6.addListenerForSingleValueEvent(valueEventListener);


    }
}
