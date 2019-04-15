package com.culcheckpot.culcheckpot;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ProMain extends AppCompatActivity {

    public static String userID;
    public static String userName;
    public static String userJob;
    public static String userNum;
    public static String userUniversity;
    public static String userMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_main);

        userID = getIntent().getStringExtra("userID");
        userName = getIntent().getStringExtra("userName");
        userJob = getIntent().getStringExtra("userJob");
        userNum = getIntent().getStringExtra("userNum");
        userUniversity = getIntent().getStringExtra("userUniversity");
        userMajor = getIntent().getStringExtra("userMajor");

        final Button timetableBtn = (Button) findViewById(R.id.timetableBtn);
        final Button attendBtn = (Button) findViewById(R.id.attendBtn);
        final Button courseBtn = (Button) findViewById(R.id.courseBtn);

        timetableBtn.setTextColor(getResources().getColor(R.color.colorGray));
        attendBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
        courseBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment ,new ProTimetable());
        fragmentTransaction.commit();

        timetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetableBtn.setTextColor(getResources().getColor(R.color.colorGray));
                attendBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
                courseBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment ,new ProTimetable());
                fragmentTransaction.commit();
            }
        });

        attendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendBtn.setTextColor(getResources().getColor(R.color.colorGray));
                timetableBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
                courseBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment ,new ProCourse());
                fragmentTransaction.commit();
            }
        });

        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseBtn.setTextColor(getResources().getColor(R.color.colorGray));
                attendBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
                timetableBtn.setTextColor(getResources().getColor(R.color.colorMidGray));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment ,new ProManageCourse());
                fragmentTransaction.commit();
            }
        });
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(this,"뒤로 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }

}
