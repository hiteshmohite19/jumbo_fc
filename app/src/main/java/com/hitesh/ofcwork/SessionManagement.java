package com.hitesh.ofcwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class SessionManagement extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String sp_name="session",user="UserName",pwd="Password",TAG="project";

    public SessionManagement(Context context){
        sharedPreferences=context.getSharedPreferences(sp_name,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void loginSession(String userName,String password){
        String u=userName,p=password;
        Log.i(TAG, "loginSession: called "+u+" "+p);
        editor.putString(user,u);
        editor.putString(pwd,p);
        editor.apply();
       // startActivity(new Intent(SessionManagement.this,SignUp.class));
    }

    public String getPassword(){
        return sharedPreferences.getString(pwd,"null");
    }
}
