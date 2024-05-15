package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME,0).edit();
                editor.remove("Username");
                editor.remove("Password");
                editor.apply();
                Intent in2 = new Intent(Home.this,MainActivity.class);
                startActivity(in2);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}