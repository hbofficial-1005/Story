package com.demo.story;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    FirebaseUser firebaseUser;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setTitle(null);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        SharedPreferenceHelper.setSharedPreferenceBoolean(SplashScreen.this, "is_start", true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result = SharedPreferenceHelper.getSharedPreferenceBoolean(SplashScreen.this, "is_user_login", false);
                if(result){
                    Intent loginIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
                else{
                    Intent loginIntent = new Intent(SplashScreen.this, Login.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}