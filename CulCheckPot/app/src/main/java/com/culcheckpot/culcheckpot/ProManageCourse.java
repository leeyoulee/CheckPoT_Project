package com.culcheckpot.culcheckpot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProManageCourse.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProManageCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProManageCourse extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //리스트어뎁터와 파싱할 때 추가해야 할 것
    private ListView ProManageCourseListView;
    private ProManageCourseListAdapter adapter;
    private List<ProManageCourseList> ProManageCourseList;

    private TextView userName;
    private Context context;

    private OnFragmentInteractionListener mListener;

    public ProManageCourse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProManageCourse.
     */
    // TODO: Rename and change types and number of parameters
    public static ProManageCourse newInstance(String param1, String param2) {
        ProManageCourse fragment = new ProManageCourse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        new BackgroundTask().execute();

        userName = (TextView) getView().findViewById(R.id.userName);
        userName.setText(ProMain.userName);

        Button enrollBtn = (Button) getView().findViewById(R.id.enrollBtn);

            //리스트뷰 관련 초기화
            ProManageCourseListView = (ListView) getView().findViewById(R.id.ProManageCourseListView); //리스트뷰를 해당 리스트뷰로 초기화시킴
            ProManageCourseList = new ArrayList<ProManageCourseList>();
            adapter = new ProManageCourseListAdapter(getContext().getApplicationContext(), ProManageCourseList, this); //해당 리스트 값을 정확히 매칭시킴
            ProManageCourseListView.setAdapter(adapter);


        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getContext(), ProEnroll.class);
                getContext().startActivity(registerIntent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pro_manage_course, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
                   // + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://leeyoulee95.cafe24.com/ProManageCourseList.php?userID=" + URLEncoder.encode(ProMain.userID, "UTF-8");
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
                String courseUniversity;
                String courseMajor;
                String courseYear;
                String courseTerm;
                String courseArea;
                String courseGrade;
                String courseTitle;
                String courseCredit;
                String courseProfessor;
                String courseTime;
                String courseRoom;
                String userID;
                int courseID;
                while (count < jsonArray.length()) //배열돌면서 원소값 대입
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseUniversity = object.getString("courseUniversity");
                    courseMajor = object.getString("courseMajor");
                    courseYear = object.getString("courseYear");
                    courseTerm = object.getString("courseTerm");
                    courseArea = object.getString("courseArea");
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseCredit = object.getString("courseCredit");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseRoom = object.getString("courseRoom");
                    userID = object.getString("userID");
                    courseID = object.getInt("courseID");
                    ProManageCourseList promanagecourselist = new ProManageCourseList(courseUniversity, courseMajor, courseYear, courseTerm, courseArea, courseGrade, courseTitle, courseCredit, courseProfessor, courseTime, courseRoom, userID, courseID);
                    ProManageCourseList.add(promanagecourselist);
                    count++;
                }
                ProManageCourseListView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
