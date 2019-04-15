package com.culcheckpot.culcheckpot;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userName;
    private String userJob;
    private String userNum;
    private String userUniversity;
    private String userMajor;
    private AlertDialog dialog;
    private Spinner jobSpinner;
    private ArrayAdapter jobAdapter;
    private Spinner universitySpinner;
    private ArrayAdapter universityAdapter;
    private Spinner majorSpinner;
    private ArrayAdapter majorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        jobSpinner = (Spinner) findViewById(R.id.jobSpinner);
        jobAdapter = ArrayAdapter.createFromResource(this, R.array.job, android.R.layout.simple_spinner_dropdown_item);
        jobSpinner.setAdapter(jobAdapter);

        universitySpinner = (Spinner) findViewById(R.id.universitySpinner);
        universityAdapter = ArrayAdapter.createFromResource(this, R.array.university, android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);

        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);
        majorAdapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText numText = (EditText) findViewById(R.id.numText);



        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userJob = jobSpinner.getSelectedItem().toString();
                String userNum = numText.getText().toString();
                String userUniversity = universitySpinner.getSelectedItem().toString();
                String userMajor = majorSpinner.getSelectedItem().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                dialog = builder.setMessage("회원가입에 성공하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(Register.this, Login.class);
                                Register.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                dialog = builder.setMessage("회원가입에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userJob, userNum ,userUniversity , userMajor, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);
            }
        });
    }
    @Override
    protected void  onStop(){
        super.onStop();
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
