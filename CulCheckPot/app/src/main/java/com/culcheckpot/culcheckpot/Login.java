package com.culcheckpot.culcheckpot;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText= (EditText) findViewById(R.id.idText);
        final EditText passwordText= (EditText) findViewById(R.id.passwordText);
        final Button loginBtn= (Button) findViewById(R.id.loginBtn);
        final TextView registerBtn = (TextView) findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userId = idText.getText().toString();
                final String userPw = passwordText.getText().toString();

             Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                final String  userID = jsonResponse.getString("userID");
                                final String  userPassword = jsonResponse.getString("userPassword");
                                final String  userName = jsonResponse.getString("userName");
                                final String  userJob = jsonResponse.getString("userJob");
                                final String  userNum = jsonResponse.getString("userNum");
                                final String  userUniversity = jsonResponse.getString("userUniversity");
                                final String  userMajor = jsonResponse.getString("userMajor");

                                if(userJob.equals("학생")) {
                                    Intent intent = new Intent(Login.this, StuMain.class);
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPassword", userPassword);
                                    intent.putExtra("userName", userName);
                                    intent.putExtra("userJob", userJob);
                                    intent.putExtra("userNum", userNum);
                                    intent.putExtra("userUniversity", userUniversity);
                                    intent.putExtra("userMajor", userMajor);
                                    Login.this.startActivity(intent);
                                }
                                else if(userJob.equals("교수")){
                                    Intent intent = new Intent(Login.this, ProMain.class);
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPassword", userPassword);
                                    intent.putExtra("userName", userName);
                                    intent.putExtra("userJob", userJob);
                                    intent.putExtra("userNum", userNum);
                                    intent.putExtra("userUniversity", userUniversity);
                                    intent.putExtra("userMajor", userMajor);
                                    Login.this.startActivity(intent);
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userId, userPw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });
    }
}
