package com.culcheckpot.culcheckpot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
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
import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ProCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<ProCourseList> ProCourseList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private String userID = ProMain.userID;
    private Fragment parent;
    private double ProLatitude;
    private double ProLongitude;
    private String ProLat;
    private String ProLng;
    GeoVariable geovariable = new GeoVariable();

    SimpleDateFormat pd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    String ProDate = pd.format(new Date());

    SimpleDateFormat pt = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
    String ProTime = pt.format(new Date());


    public ProCourseListAdapter(Context context, List<ProCourseList> ProCourseList, Fragment parent) {
        this.context = context;
        this.ProCourseList = ProCourseList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return ProCourseList.size();
    }

    @Override
    public Object getItem(int i) {
        return ProCourseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.procourselist, null);

        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseArea = (TextView) v.findViewById(R.id.courseArea);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);

        courseTitle.setText(ProCourseList.get(i).getCourseTitle()); //클래스에 리스트값 중 타이틀값을 가져옴
        courseTime.setText(ProCourseList.get(i).getCourseTime());
        courseRoom.setText(ProCourseList.get(i).getCourseRoom());
        courseGrade.setText(ProCourseList.get(i).getCourseGrade());
        courseArea.setText(ProCourseList.get(i).getCourseArea());
        courseCredit.setText(ProCourseList.get(i).getCourseCredit());
        courseProfessor.setText(ProCourseList.get(i).getCourseProfessor());

        v.setTag(ProCourseList.get(i).getCourseID()); //현재리스트에서 강의아이디값을 태그로 함

        // Acquire a reference to the system Location Manager
        LocationManager ProLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = ProLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = ProLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        //Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);


        LocationListener ProLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                ProLatitude = location.getLatitude();
                ProLongitude = location.getLongitude();

                //Log.d("location_double2", "lat" + ProLatitude + "lng" + ProLongitude);
                //logView.setText("latitude: " + lat + ", longitude: " + lng);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("onStatusChanged","onStatusChanged");
                //logView.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                Log.d("onProviderEnabled","onProviderEnabled");
                //logView.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                Log.d("onProviderDisabled","onProviderDisabled");
                //logView.setText("onProviderDisabled");
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return v;
        }
        ProLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ProLocationListener);
        ProLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ProLocationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = ProLocationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            ProLatitude = lastKnownLocation.getLatitude();
            ProLongitude = lastKnownLocation.getLongitude();
            // Log.d("Main", "longitude=" + ProLatitude + ", latitude=" + ProLongitude);
        }

        Button attendBtn = (Button) v.findViewById(R.id.attendBtn);
        attendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("출석이 시작되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("출석에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                ProLat = Double.toString(ProLatitude);
                ProLng = Double.toString(ProLongitude);

                Log.d("location", "lat" + ProLat + "lng" + ProLng);

                ProAttendRequest proAttendRequest = new ProAttendRequest(userID, ProCourseList.get(i).getCourseID() + "", ProDate, ProTime, ProLat, ProLng, responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(proAttendRequest);

            }
        });

        Button checkBtn = (Button) v.findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = ProCourseList.get(i).getCourseTitle();
                int courseID = ProCourseList.get(i).getCourseID();
                Intent attendIntent = new Intent(parent.getContext(), ProCheck.class);
                parent.getContext().startActivity(attendIntent);
                ProCheck.sendCourseTitle(courseTitle);
                ProCheck.sendCourseID(courseID);
            }
        });

        return v;
    }
}

