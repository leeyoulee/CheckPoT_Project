package com.culcheckpot.culcheckpot;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import static com.culcheckpot.culcheckpot.ProMain.userID;
import static com.culcheckpot.culcheckpot.ProMain.userName;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StuEnroll.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StuEnroll#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StuEnroll extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StuEnroll() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StuEnroll.
     */
    // TODO: Rename and change types and number of parameters
    public static StuEnroll newInstance(String param1, String param2) {
        StuEnroll fragment = new StuEnroll();
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

    private Spinner universitySpinner;
    private ArrayAdapter universityAdapter;
    private Spinner majorSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter yearAdapter;
    private Spinner termSpinner;
    private ArrayAdapter termAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter areaAdapter;

    private TextView userName;


    private String courseUniversity;
    private String courseYear;
    private String courseTerm;
    private String courseArea;

    //리스트어뎁터와 파싱할 때 추가해야 할 것
    private ListView StuCourseListView;
    private StuCourseListAdapter adapter;
    private List<StuCourseList> StuCourseList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        universitySpinner = (Spinner) getView().findViewById(R.id.universitySpinner);
        universityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.university, android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);

        majorSpinner = (Spinner) getView().findViewById(R.id.majorSpinner);
        majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.major, android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        termSpinner = (Spinner) getView().findViewById(R.id.termSpinner);
        termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.term, android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(termAdapter);

        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.area, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        //리스트뷰 관련 초기화
        StuCourseListView = (ListView) getView().findViewById(R.id.StuCourseListView); //리스트뷰를 해당 리스트뷰로 초기화시킴
        StuCourseList = new ArrayList<StuCourseList>();
        adapter = new StuCourseListAdapter(getContext().getApplicationContext(), StuCourseList, this); //해당 리스트 값을 정확히 매칭시킴
        StuCourseListView.setAdapter(adapter);

        userName = (TextView) getView().findViewById(R.id.userName);
        userName.setText(StuMain.userName);

        Button searchBtn = (Button) getView().findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stu_enroll, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
                target = "http://leeyoulee95.cafe24.com/CourseList.php?courseUniversity=" + URLEncoder.encode(universitySpinner.getSelectedItem().toString(), "UTF-8")
                        + "&courseMajor=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8")
                        + "&courseYear=" + URLEncoder.encode(yearSpinner.getSelectedItem().toString(), "UTF-8")
                        + "&courseTerm=" + URLEncoder.encode(termSpinner.getSelectedItem().toString(), "UTF-8")
                        + "&courseArea=" + URLEncoder.encode(areaSpinner.getSelectedItem().toString(), "UTF-8");
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
                StuCourseList.clear(); //해당 강의목록을 없애줌
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");//배열 생성하여 response 라는 배열의 이름으로 받아줌
                int count = 0;
                //StuCourseList 안에 있는 변수 그대로 넣어줌
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
                while (count < jsonArray.length())
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
                    StuCourseList stucourselist = new StuCourseList(courseUniversity, courseMajor, courseYear, courseTerm, courseArea, courseGrade, courseTitle, courseCredit, courseProfessor, courseTime, courseRoom, userID, courseID);
                    StuCourseList.add(stucourselist);
                    count++;
                }
                if(count==0)
                {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(StuEnroll.this.getActivity());
                    dialog = builder.setMessage("조회된 강의가 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                adapter.notifyDataSetChanged(); //값이 변경되었다는 걸 어뎁터에 알려줌

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.add_item:
                // do something
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
