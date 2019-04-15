package com.culcheckpot.culcheckpot;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class StuCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<StuCourseList> StuCourseList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private Fragment parent;
    private String userID = StuMain.userID;
    private StuSchedule stuschedule = new StuSchedule();
    private List<Integer> courseIDList;


    public StuCourseListAdapter(Context context, List<StuCourseList> StuCourseList, Fragment parent) {
        this.context = context;
        this.StuCourseList = StuCourseList;
        this.parent = parent;
        stuschedule = new StuSchedule();
        courseIDList = new ArrayList<Integer>();

        new BackgroundTask().execute();
    }

    @Override
    public int getCount() {
        return StuCourseList.size();
    }

    @Override
    public Object getItem(int i) {
        return StuCourseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.stucourselist, null);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseArea = (TextView) v.findViewById(R.id.courseArea);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);

        courseTitle.setText(StuCourseList.get(i).getCourseTitle()); //클래스에 리스트값 중 타이틀값을 가져옴
        courseTime.setText(StuCourseList.get(i).getCourseTime());
        courseRoom.setText(StuCourseList.get(i).getCourseRoom());
        courseGrade.setText(StuCourseList.get(i).getCourseGrade());
        courseArea.setText(StuCourseList.get(i).getCourseArea());
        courseCredit.setText(StuCourseList.get(i).getCourseCredit());
        courseProfessor.setText(StuCourseList.get(i).getCourseProfessor());

        v.setTag(StuCourseList.get(i).getCourseID()); //현재리스트에서 강의아이디값을 태그로 함

        Button addBtn = (Button) v.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean validate = false;
                validate = stuschedule.validate(StuCourseList.get(i).getCourseTime());
                if (!alreadyIn(courseIDList, StuCourseList.get(i).getCourseID()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("강의를 중복해서 추가할 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    }
                else if (validate == false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("시간이 중복되는 강의가 있습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                else
                    {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    courseIDList.add(StuCourseList.get(i).getCourseID());
                                    stuschedule.addSchedule(StuCourseList.get(i).getCourseTime());
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    StuEnrollRequest stuEnrollRequest = new StuEnrollRequest(userID, StuCourseList.get(i).getCourseID() + "", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(stuEnrollRequest);
                }
            }
        });

        return v;

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://leeyoulee95.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
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
                String courseProfessor;
                String courseTime;
                String courseTitle;
                int courseID;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseTitle = object.getString("courseTitle");
                    courseID = object.getInt("courseID");
                    courseIDList.add(courseID);
                    stuschedule.addSchedule(courseTime);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean alreadyIn(List<Integer> courseIDList, int item) {
        for (int i = 0; i < courseIDList.size(); i++) {
            if (courseIDList.get(i) == item) {
                return false;
            }
        }
        return true;
    }

}
