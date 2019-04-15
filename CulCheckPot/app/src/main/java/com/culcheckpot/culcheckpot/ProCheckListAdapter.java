package com.culcheckpot.culcheckpot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ProCheckListAdapter extends BaseAdapter {

    private Context context;
    private List<ProCheckList> ProCheckList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private String userID = ProMain.userID;
    private AppCompatActivity parent;


    public ProCheckListAdapter(Context context, List<ProCheckList> ProCheckList, AppCompatActivity parent) {
        this.context = context;
        this.ProCheckList = ProCheckList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return ProCheckList.size();
    }

    @Override
    public Object getItem(int i) {
        return ProCheckList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.prochecklist, null);

        final TextView courseDate = (TextView) v.findViewById(R.id.courseDate);
        courseDate.setText(ProCheckList.get(i).getProDate()); //클래스에 리스트값 중 타이틀값을 가져옴

        Button attendListBtn = (Button) v.findViewById(R.id.attendListBtn);
        attendListBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(parent, ProAttendCheck.class);
                parent.startActivity(registerIntent);
                String CourseDate = ProCheckList.get(i).getProDate();
                int CourseID = ProCheckList.get(i).getCourseID();
                ProAttendCheck.sendCourseDate(CourseDate);
                ProAttendCheck.sendCourseID(CourseID);
            }
        });
        return v;
    }
}

