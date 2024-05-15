 package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

 public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME,0);
                //boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);
                String Username = sharedPreferences.getString("Username","");
                String Password = sharedPreferences.getString("Password","");

                if (Username !="" & Password !="")
                {
                    Intent intent = new Intent(SplashScreen.this,Home.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
}