package com.culcheckpot.culcheckpot;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.culcheckpot.culcheckpot.StuMain.userNum;


public class StuDeleteListAdapter extends BaseAdapter {

    private Fragment parent;
    public Context context;
    private List<StuDeleteList> StuDeleteList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private String userID = StuMain.userID;
    public int courseID;



    public StuDeleteListAdapter(Context context, List<StuDeleteList> StuDeleteList, Fragment parent) {
        this.context = context;
        this.StuDeleteList = StuDeleteList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return StuDeleteList.size();
    }

    @Override
    public Object getItem(int i) {
        return StuDeleteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.studeletelist, null);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseArea = (TextView) v.findViewById(R.id.courseArea);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);

        courseTitle.setText(StuDeleteList.get(i).getCourseTitle()); //클래스에 리스트값 중 타이틀값을 가져옴
        courseTime.setText(StuDeleteList.get(i).getCourseTime());
        courseRoom.setText(StuDeleteList.get(i).getCourseRoom());
        courseGrade.setText(StuDeleteList.get(i).getCourseGrade());
        courseArea.setText(StuDeleteList.get(i).getCourseArea());
        courseCredit.setText(StuDeleteList.get(i).getCourseCredit());
        courseProfessor.setText(StuDeleteList.get(i).getCourseProfessor());


        v.setTag(StuDeleteList.get(i).getCourseID()); //현재리스트에서 강의아이디값을 태그로 함

        Button StuDeleteBtn = (Button) v.findViewById(R.id.StuDeleteBtn);
        StuDeleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                courseID = StuDeleteList.get(i).getCourseID();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                                AlertDialog dialog = builder.setMessage("강의를 삭제하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                                AlertDialog dialog = builder.setMessage("강의삭제에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                StuDeleteRequest studeleteRequest = new StuDeleteRequest(StuMain.userID, courseID + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(studeleteRequest);

            }
        });
        return v;
    }


}
