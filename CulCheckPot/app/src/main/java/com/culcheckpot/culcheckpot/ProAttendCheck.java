package com.culcheckpot.culcheckpot;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProAttendCheck extends AppCompatActivity {

    //리스트어뎁터와 파싱할 때 추가해야 할 것
    private ListView ProAttendCheckListView;
    private ProAttendCheckListAdapter adapter;
    private List<ProAttendCheckList> ProAttendCheckList;
    private static String CourseDate;
    private static int CourseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_attend_check);

        //리스트뷰 관련 초기화
        ProAttendCheckListView = (ListView) findViewById(R.id.ProAttendCheckListView); //리스트뷰를 해당 리스트뷰로 초기화시킴
        ProAttendCheckList = new ArrayList<ProAttendCheckList>();
        adapter = new ProAttendCheckListAdapter(getApplication().getApplicationContext(), ProAttendCheckList, this); //해당 리스트 값을 정확히 매칭시킴
        ProAttendCheckListView.setAdapter(adapter);

        TextView courseDate = (TextView) findViewById(R.id.courseDate);
        courseDate.setText(CourseDate);

        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://leeyoulee95.cafe24.com/ProAttendCheck.php?courseID=" + URLEncoder.encode(CourseID + "", "UTF-8")
                + "&StuDate=" + URLEncoder.encode(CourseDate, "UTF-8");
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
                String userName;
                String userNum;
                String attendText;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    userName = object.getString("userName");
                    userNum = object.getString("userNum");
                    attendText = object.getString("attendText");

                    //Log.d("attendcheck2", userName + " " + userNum + " " + attendText);
                    ProAttendCheckList proattendchecklist = new ProAttendCheckList(userName, userNum, attendText);
                    ProAttendCheckList.add(proattendchecklist);
                    count++;
                }
                ProAttendCheckListView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendCourseDate(String Date){
        CourseDate = Date;
        return CourseDate;
    }

    public static int sendCourseID(int ID){
        CourseID = ID;
        return CourseID;
    }
}
