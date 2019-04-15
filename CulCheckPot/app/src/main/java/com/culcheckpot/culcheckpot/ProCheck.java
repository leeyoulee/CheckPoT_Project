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
import java.util.ArrayList;
import java.util.List;

import static com.culcheckpot.culcheckpot.ProMain.userID;


public class ProCheck extends AppCompatActivity {

    //리스트어뎁터와 파싱할 때 추가해야 할 것
    private ListView ProCheckListView;
    private ProCheckListAdapter adapter;
    private List<ProCheckList> ProCheckList;
    private TextView courseTitle;
    private static String CourseTitle;
    private static int CourseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_check);

        courseTitle = (TextView) findViewById(R.id.courseTitle);
        courseTitle.setText(CourseTitle);

        //리스트뷰 관련 초기화
        ProCheckListView = (ListView) findViewById(R.id.ProCheckListView); //리스트뷰를 해당 리스트뷰로 초기화시킴
        ProCheckList = new ArrayList<ProCheckList>();
        adapter = new ProCheckListAdapter(getApplication().getApplicationContext(), ProCheckList, this); //해당 리스트 값을 정확히 매칭시킴
        ProCheckListView.setAdapter(adapter);

        new BackgroundTask().execute();

        Log.d("test", CourseID + " ");
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://leeyoulee95.cafe24.com/ProCheckList.php?userID=" + URLEncoder.encode(userID, "UTF-8")
                + "&courseID=" + URLEncoder.encode(CourseID + " ", "UTF-8");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }  catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {super.onProgressUpdate(); }

        @Override
        public void onPostExecute(String result){
            try{ //데이터 파싱하는 부분임
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");//배열 생성하여 response 라는 배열의 이름으로 받아줌
                int count = 0;
                //ProCourseList 안에 있는 변수 그대로 넣어줌
                int courseID;
                String ProDate;
                while (count < jsonArray.length()) //배열돌면서 원소값 대입
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    ProDate = object.getString("ProDate");
                    ProCheckList prochecklist = new ProCheckList(courseID, ProDate);
                    ProCheckList.add(prochecklist);
                    count++;
                }
                ProCheckListView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendCourseTitle(String Title){
        CourseTitle = Title;
        return CourseTitle;
    }

    public static int sendCourseID(int ID){
        CourseID = ID;
        return CourseID;
    }
}
