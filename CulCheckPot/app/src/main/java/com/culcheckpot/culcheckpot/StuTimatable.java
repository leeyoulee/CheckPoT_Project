package com.culcheckpot.culcheckpot;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StuTimatable.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StuTimatable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StuTimatable extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StuTimatable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StuTimatable.
     */
    // TODO: Rename and change types and number of parameters
    public static StuTimatable newInstance(String param1, String param2) {
        StuTimatable fragment = new StuTimatable();
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

    private TextView monday[] = new TextView[9];
    private TextView tuesday[] = new TextView[9];
    private TextView wednesday[] = new TextView[9];
    private TextView thursday[] = new TextView[9];
    private TextView friday[] = new TextView[9];
    private StuSchedule stuschedule = new StuSchedule();
    private TextView userName;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        monday[0] = (TextView) getView().findViewById(R.id.monday0);
        monday[1] = (TextView) getView().findViewById(R.id.monday1);
        monday[2] = (TextView) getView().findViewById(R.id.monday2);
        monday[3] = (TextView) getView().findViewById(R.id.monday3);
        monday[4] = (TextView) getView().findViewById(R.id.monday4);
        monday[5] = (TextView) getView().findViewById(R.id.monday5);
        monday[6] = (TextView) getView().findViewById(R.id.monday6);
        monday[7] = (TextView) getView().findViewById(R.id.monday7);
        monday[8] = (TextView) getView().findViewById(R.id.monday8);

        tuesday[0] = (TextView) getView().findViewById(R.id.tuesday0);
        tuesday[1] = (TextView) getView().findViewById(R.id.tuesday1);
        tuesday[2] = (TextView) getView().findViewById(R.id.tuesday2);
        tuesday[3] = (TextView) getView().findViewById(R.id.tuesday3);
        tuesday[4] = (TextView) getView().findViewById(R.id.tuesday4);
        tuesday[5] = (TextView) getView().findViewById(R.id.tuesday5);
        tuesday[6] = (TextView) getView().findViewById(R.id.tuesday6);
        tuesday[7] = (TextView) getView().findViewById(R.id.tuesday7);
        tuesday[8] = (TextView) getView().findViewById(R.id.tuesday8);

        wednesday[0] = (TextView) getView().findViewById(R.id.wednesday0);
        wednesday[1] = (TextView) getView().findViewById(R.id.wednesday1);
        wednesday[2] = (TextView) getView().findViewById(R.id.wednesday2);
        wednesday[3] = (TextView) getView().findViewById(R.id.wednesday3);
        wednesday[4] = (TextView) getView().findViewById(R.id.wednesday4);
        wednesday[5] = (TextView) getView().findViewById(R.id.wednesday5);
        wednesday[6] = (TextView) getView().findViewById(R.id.wednesday6);
        wednesday[7] = (TextView) getView().findViewById(R.id.wednesday7);
        wednesday[8] = (TextView) getView().findViewById(R.id.wednesday8);

        thursday[0] = (TextView) getView().findViewById(R.id.thursday0);
        thursday[1] = (TextView) getView().findViewById(R.id.thursday1);
        thursday[2] = (TextView) getView().findViewById(R.id.thursday2);
        thursday[3] = (TextView) getView().findViewById(R.id.thursday3);
        thursday[4] = (TextView) getView().findViewById(R.id.thursday4);
        thursday[5] = (TextView) getView().findViewById(R.id.thursday5);
        thursday[6] = (TextView) getView().findViewById(R.id.thursday6);
        thursday[7] = (TextView) getView().findViewById(R.id.thursday7);
        thursday[8] = (TextView) getView().findViewById(R.id.thursday8);

        friday[0] = (TextView) getView().findViewById(R.id.friday0);
        friday[1] = (TextView) getView().findViewById(R.id.friday1);
        friday[2] = (TextView) getView().findViewById(R.id.friday2);
        friday[3] = (TextView) getView().findViewById(R.id.friday3);
        friday[4] = (TextView) getView().findViewById(R.id.friday4);
        friday[5] = (TextView) getView().findViewById(R.id.friday5);
        friday[6] = (TextView) getView().findViewById(R.id.friday6);
        friday[7] = (TextView) getView().findViewById(R.id.friday7);
        friday[8] = (TextView) getView().findViewById(R.id.friday8);

        TextView userName = (TextView) getView().findViewById(R.id.userName);
        userName.setText(StuMain.userName);

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://leeyoulee95.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(StuMain.userID, "UTF-8");
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
                    stuschedule.addSchedule(courseTime, courseTitle, courseProfessor);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            stuschedule.setting(monday, tuesday, wednesday, thursday, friday, getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stu_timatable, container, false);
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
}
