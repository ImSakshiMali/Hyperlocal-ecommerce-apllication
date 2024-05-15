package com.example.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText er_name, er_username, er_password, er_cpassword, er_mobile;
    TextView er_login;
    Button er_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        er_name = findViewById(R.id.er_name);
        er_username = findViewById(R.id.er_username);
        er_password = findViewById(R.id.er_password);
        er_cpassword = findViewById(R.id.er_cpassword);
        er_mobile = findViewById(R.id.er_mobile);
        er_login = findViewById(R.id.er_login);
        er_register = findViewById(R.id.er_register);

        er_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        er_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = er_name.getText().toString().trim();
                String Username = er_username.getText().toString().trim();
                String Mobile = er_mobile.getText().toString().trim();
                String Password = er_password.getText().toString().trim();
                String ConfirmPassword = er_cpassword.getText().toString().trim();

                ProgressDialog progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("Please Wait");
                progressDialog.show();

                if (Name.isEmpty()) {
                    Toast.makeText(Register.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (Username.isEmpty()) {
                    Toast.makeText(Register.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (Mobile.isEmpty()) {
                    Toast.makeText(Register.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (Password.isEmpty()) {
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (ConfirmPassword.isEmpty()) {
                    Toast.makeText(Register.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    StringRequest request = new StringRequest(Request.Method.POST, "http://tsm.ecssofttech.com/Library/api/Ecom_Register.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String s1 = "Record Inserted Successfully";
                            String s2 = "User Already Exist";

                            if (response.equalsIgnoreCase(s2)) {
                                Toast.makeText(Register.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                                er_name.setText("");
                                er_username.setText("");
                                er_mobile.setText("");
                                er_password.setText("");
                                er_cpassword.setText("");
                                progressDialog.dismiss();
                            } else if (response.equalsIgnoreCase(s1)) {
                                Toast.makeText(Register.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                //SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME,0);
                                //SharedPreferences.Editor editor = sharedPreferences.edit();
                                //editor.putString("userName",userName);
                                //editor.putString("password",password);
                                //editor.apply();
                                //editor.commit();

                                Intent intent = new Intent(Register.this, Home.class);
                                intent.putExtra("Username", Username);
                                intent.putExtra("Password", Password);
                                startActivity(intent);
                                finish();
                                er_name.setText("");
                                er_username.setText("");
                                er_mobile.setText("");
                                er_password.setText("");
                                er_cpassword.setText("");

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            params.put("Name", Name);
                            params.put("Username", Username);
                            params.put("Mobile", Mobile);
                            params.put("Password", Password);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                    requestQueue.add(request);
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Register.this, MainActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}