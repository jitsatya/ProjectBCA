package com.projects.satyajit.projectbca;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context)
    {
        this.context=context;
        sharedPref = context.getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setLoggedIn(boolean loggedIn)
    {
        editor.putBoolean("loggedInMode", loggedIn);
        editor.commit();
    }
    public boolean loggedIn(){
        return sharedPref.getBoolean("loggedInMode", false);
    }
}
