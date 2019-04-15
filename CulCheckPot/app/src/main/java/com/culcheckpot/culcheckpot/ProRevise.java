package com.culcheckpot.culcheckpot;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static com.culcheckpot.culcheckpot.ProMain.userID;
import static com.culcheckpot.culcheckpot.ProMain.userName;

public class ProRevise extends AppCompatActivity{

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
    private Spinner creditSpinner;
    private ArrayAdapter creditAdapter;
    private Spinner gradeSpinner;
    private ArrayAdapter gradeAdapter;
    private TextView userNameText;
    private static int CourseID;
    private static String CourseTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_revise);


        universitySpinner = (Spinner) findViewById(R.id.universitySpinner);
        universityAdapter = ArrayAdapter.createFromResource(ProRevise.this, R.array.university, android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);

        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);
        majorAdapter = ArrayAdapter.createFromResource(ProRevise.this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        yearAdapter = ArrayAdapter.createFromResource(ProRevise.this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        termSpinner = (Spinner) findViewById(R.id.termSpinner);
        termAdapter = ArrayAdapter.createFromResource(ProRevise.this, R.array.term, android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(termAdapter);

        areaSpinner = (Spinner) findViewById(R.id.areaSpinner);
        areaAdapter = ArrayAdapter.createFromResource(ProRevise.this, R.array.area, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        creditSpinner = (Spinner) findViewById(R.id.creditSpinner);
        creditAdapter = ArrayAdapter.createFromResource(ProRevise.this, R.array.credit, android.R.layout.simple_spinner_dropdown_item);
        creditSpinner.setAdapter(creditAdapter);

        gradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        gradeAdapter = ArrayAdapter.createFromResource(ProRevise.this, R.array.grade, android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        userNameText = (TextView) findViewById(R.id.userNameText);
        userNameText.setText(CourseTitle);

        final EditText timeText = (EditText) findViewById(R.id.timeText);
        final EditText roomText = (EditText) findViewById(R.id.roomText);
        final EditText titleText = (EditText) findViewById(R.id.titleText);


        Button reviseBtn = (Button) findViewById(R.id.reviseBtn);
        reviseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String courseUniversity = universitySpinner.getSelectedItem().toString();
                String courseMajor = majorSpinner.getSelectedItem().toString();
                String courseYear = yearSpinner.getSelectedItem().toString();
                String courseTerm = termSpinner.getSelectedItem().toString();
                String courseArea = areaSpinner.getSelectedItem().toString();
                String courseGrade = gradeSpinner.getSelectedItem().toString();
                String courseCredit = creditSpinner.getSelectedItem().toString();
                String courseTime = timeText.getText().toString();
                String courseRoom = roomText.getText().toString();
                String courseTitle = titleText.getText().toString();
                String courseProfessor = userName;
                String courseID = String.valueOf(CourseID);

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProRevise.this);
                                builder.setMessage("강의 수정에 성공하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProRevise.this);
                                builder.setMessage("강의 수정에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ProReviseRequest ProReviseRequest = new ProReviseRequest(courseUniversity, courseMajor, courseYear, courseTerm, courseArea, courseGrade, courseTitle, courseCredit, courseProfessor, courseTime, courseRoom, ProMain.userID, courseID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProRevise.this);
                queue.add(ProReviseRequest);
            }
        });
    }

    public static int sendCourseID(int ID){
        CourseID = ID;
        return CourseID;
    }

    public static String sendCourseTitle(String Title){
        CourseTitle = Title;
        return CourseTitle;
    }
}
