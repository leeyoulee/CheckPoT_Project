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


public class ProAttendCheckListAdapter extends BaseAdapter {

    private Context context;
    private List<ProAttendCheckList> ProAttendCheckList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private String userID = ProMain.userID;
    private AppCompatActivity parent;


    public ProAttendCheckListAdapter(Context context, List<ProAttendCheckList> ProAttendCheckList, AppCompatActivity parent) {
        this.context = context;
        this.ProAttendCheckList = ProAttendCheckList;
        this.parent = parent;
    }


    @Override
    public int getCount() {
        return ProAttendCheckList.size();
    }

    @Override
    public Object getItem(int i) {
        return ProAttendCheckList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.proattendchecklist, null);

        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView userNum = (TextView) v.findViewById(R.id.userNum);
        TextView attendText = (TextView) v.findViewById(R.id.attendText);

        userName.setText(ProAttendCheckList.get(i).getUserName());
        userNum.setText(ProAttendCheckList.get(i).getUserNum());
        attendText.setText(ProAttendCheckList.get(i).getAttendText());
        return v;
    }
}

