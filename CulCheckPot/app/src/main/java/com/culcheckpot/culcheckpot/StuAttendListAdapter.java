package com.culcheckpot.culcheckpot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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


public class StuAttendListAdapter extends BaseAdapter {

    private Fragment parent;
    public Context context;
    private List<StuAttendList> StuAttendList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private String userID = StuMain.userID;
    public int courseID;
    public double StuLatitude;
    public double StuLongitude;
    public Date prodate;
    public Date studate;
    public Date protime;
    public Date stutime;
    double distance;
    int meter;
    String attendText;
    public String ProLatitude;
    GeoVariable geovariable = new GeoVariable();

    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    String StuDate = sd.format(new Date());

    SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
    String StuTime = st.format(new Date());


    public StuAttendListAdapter(Context context, List<StuAttendList> StuAttendList, Fragment parent) {
        this.context = context;
        this.StuAttendList = StuAttendList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return StuAttendList.size();
    }

    @Override
    public Object getItem(int i) {
        return StuAttendList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.stuattendlist, null);
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseArea = (TextView) v.findViewById(R.id.courseArea);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);

        courseTitle.setText(StuAttendList.get(i).getCourseTitle()); //클래스에 리스트값 중 타이틀값을 가져옴
        courseTime.setText(StuAttendList.get(i).getCourseTime());
        courseRoom.setText(StuAttendList.get(i).getCourseRoom());
        courseGrade.setText(StuAttendList.get(i).getCourseGrade());
        courseArea.setText(StuAttendList.get(i).getCourseArea());
        courseCredit.setText(StuAttendList.get(i).getCourseCredit());
        courseProfessor.setText(StuAttendList.get(i).getCourseProfessor());


        v.setTag(StuAttendList.get(i).getCourseID()); //현재리스트에서 강의아이디값을 태그로 함
        // Acquire a reference to the system Location Manager
        LocationManager stulocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = stulocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = stulocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        //Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);


        LocationListener stulocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                StuLatitude = location.getLatitude();
                StuLongitude = location.getLongitude();

                Log.d("location_double", "lat" + StuLatitude + "lng" + StuLongitude);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("onStatusChanged","onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                Log.d("onProviderEnabled","onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                Log.d("onProviderDisabled","onProviderDisabled");
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
        stulocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, stulocationListener);
        stulocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, stulocationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = stulocationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            StuLatitude = lastKnownLocation.getLatitude();
            StuLongitude = lastKnownLocation.getLongitude();
            // Log.d("Main", "longitude=" + ProLatitude + ", latitude=" + ProLongitude);
        }


        Button StuAttendBtn = (Button) v.findViewById(R.id.StuAttendBtn);
        StuAttendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                courseID = StuAttendList.get(i).getCourseID();

                new BackgroundTask().execute();

               //Log.d("test", " " + ProDate + " " + ProTime + " " + " " + ProLatitude + " " + ProLongitude);

            }
        });

        Button StuCheckBtn = (Button) v.findViewById(R.id.StuCheckBtn);
        StuCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int courseID = StuAttendList.get(i).getCourseID();
                String Title = StuAttendList.get(i).getCourseTitle();
                Log.d("test", courseID + " " + Title);
                Intent attendIntent = new Intent(parent.getContext(), StuCheck.class);
                parent.getContext().startActivity(attendIntent);
                StuCheck.sendCourseTitle(Title);
                StuCheck.sendCourseID(courseID);
            }
        });
        return v;
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://leeyoulee95.cafe24.com/StuAttend.php?courseID=" + URLEncoder.encode(courseID + "", "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String ProDate;
                String ProTime;
                String ProLatitude;
                String ProLongitude;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ProDate = object.getString("ProDate");
                    ProTime = object.getString("ProTime");
                    ProLatitude = object.getString("ProLatitude");
                    ProLongitude = object.getString("ProLongitude");
                    SimpleDateFormat transFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                    studate = transFormat1.parse(StuDate);
                    SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    prodate = transFormat2.parse(ProDate);
                    SimpleDateFormat transFormat3 = new SimpleDateFormat("HH:mm:ss");
                    stutime = transFormat3.parse(StuTime);
                    SimpleDateFormat transFormat4 = new SimpleDateFormat("HH:mm:ss");
                    protime = transFormat4.parse(ProTime);
                    Log.d("back",userID + " " + courseID+ " " +studate+ " " +stutime+ " " +StuLatitude+ " " +StuLongitude+ " " +prodate+ " " +protime+ " " +ProLatitude+ " " +ProLongitude);
                    attend( userID,  courseID,  studate,  stutime,  StuLatitude,  StuLongitude,  prodate,  protime,  ProLatitude,  ProLongitude);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SendAttendList(String userID, Integer courseID, String StuDate, final String attendText) {


        Log.d("Protest2", " " + userID + " " + courseID + " "  + StuDate + " " + attendText);

            String URL;
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                        AlertDialog dialog = builder.setMessage(" " + attendText + " ")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                        AlertDialog dialog = builder.setMessage(" " + attendText + " ")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        StuAttendRequest stuattendRequest = new StuAttendRequest(userID, userNum,StuMain.userName,courseID + "", StuDate, attendText, responseListener);
        RequestQueue queue = Volley.newRequestQueue(parent.getContext());
        queue.add(stuattendRequest);


        return;
    }


    public void attend(String userID, Integer courseID, Date studate, Date stutime, Double StuLatitude, Double StuLongitude, Date prodate, Date protime, String ProLatitude, String ProLongitude) {
        //Log.d("test", " " + studate + " " + stutime + " " + " " + StuLatitude + " " + StuLongitude + "\n 여기부터 Pro " + prodate + " " + protime + " " + " " + ProLatitude + " " + ProLongitude);


        double ProLat = Double.parseDouble(ProLatitude);
        double ProLng = Double.parseDouble(ProLongitude);

        Location locationStu = new Location("point Stu");
        locationStu.setLatitude(StuLatitude);
        locationStu.setLongitude(StuLongitude);

        Location locationPro = new Location("point Pro");
        locationPro.setLatitude(ProLat);
        locationPro.setLongitude(ProLng);

        distance = locationStu.distanceTo(locationPro);
        meter = Integer.parseInt(String.valueOf(Math.round(distance)));

        if (studate.compareTo(prodate) > 0) {
            Log.d("attend", "출석불가");
        } else if (studate.compareTo(prodate) < 0) {
            Log.d("attend", "출석불가");
        } else {
            long diff = stutime.getTime() - protime.getTime();
            long min = diff / 100000; //분 구하기
            if (min <= 10) {
                Log.d("time", " " + min);
                if (meter < 10) {
                    Log.d("distance", " " + meter);
                    Log.d("attend", "출석");
                    attendText = "결석";
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String StuDate = sd.format(studate);
                    SendAttendList(userID, courseID, StuDate, attendText);
                    Log.d("Protest", " " + userID + " " + courseID + " " + " " + StuDate + " " + attendText);
                } else {
                    Log.d("distance", " " + meter);
                    Log.d("attend", "결석");
                    attendText = "결석";
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String StuDate = sd.format(studate);
                    SendAttendList(userID, courseID, StuDate, attendText);
                    Log.d("Protest", " " + userID + " " + courseID + " " + " " + StuDate + " " + attendText);
                }

            } else if (min > 10 && min <= 30) {
                Log.d("time", " " + min);
                if (meter < 10) {
                    Log.d("distance", " " + meter);
                    Log.d("attend", "지각");
                    attendText = "지각";
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String StuDate = sd.format(studate);
                    SendAttendList(userID, courseID, StuDate, attendText);
                    Log.d("Protest", " " + userID + " " + courseID + " " + " " + StuDate + " " + attendText);
                } else {
                    Log.d("distance", " " + meter);
                    Log.d("attend", "결석");
                    attendText = "결석";
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String StuDate = sd.format(studate);
                    SendAttendList(userID, courseID, StuDate, attendText);
                    Log.d("Protest", " " + userID + " " + courseID + " " + " " + StuDate + " " + attendText);
                }
            } else if (min > 30) {
                Log.d("attend", "결석");
                Log.d("time", " " + min);
                attendText = "결석";
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                String StuDate = sd.format(studate);
                SendAttendList(userID, courseID, StuDate, attendText);
                Log.d("Protest", " " + userID + " " + courseID + " " + " " + StuDate + " " + attendText);
            }
        }
    }
}
