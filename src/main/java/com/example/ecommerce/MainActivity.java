package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
EditText et_user,et_password;
TextView et_register;
Button et_login;
    public  static  String PREFS_NAME="MyPrefsFile";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        et_login = findViewById(R.id.et_login);
        et_register = findViewById(R.id.et_register);

        et_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

        et_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username = et_user.getText().toString().trim();
                String Password = et_password.getText().toString().trim();

                if (Username.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }
                else if (Password.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://tsm.ecssofttech.com/Library/api/Ecom_Login.php?Username=" + Username + "&Password=" + Password + "")
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String responseString = Objects.requireNonNull(response.body()).string();
                        //System.out.println(responseString);
                        String str = "\tSuccess";
                        if (responseString.equals(str)) {

                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Username", Username);
                            editor.putString("Password", Password);
                            editor.apply();
                            editor.commit();

                            Intent in = new Intent(MainActivity.this, Home.class);
                            in.putExtra("Username", Username);
                            in.putExtra("Password", Password);
                            startActivity(in);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter Correct Username and Password", Toast.LENGTH_SHORT).show();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}