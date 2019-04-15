package com.culcheckpot.culcheckpot;

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

import java.util.List;

import static com.culcheckpot.culcheckpot.ProMain.userID;
import static com.culcheckpot.culcheckpot.ProMain.userName;

public class ProEnroll extends AppCompatActivity {

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
    private List<Integer> courseIDList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_enroll);


        universitySpinner = (Spinner) findViewById(R.id.universitySpinner);
        universityAdapter = ArrayAdapter.createFromResource(ProEnroll.this, R.array.university, android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);

        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);
        majorAdapter = ArrayAdapter.createFromResource(ProEnroll.this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        yearAdapter = ArrayAdapter.createFromResource(ProEnroll.this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        termSpinner = (Spinner) findViewById(R.id.termSpinner);
        termAdapter = ArrayAdapter.createFromResource(ProEnroll.this, R.array.term, android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(termAdapter);

        areaSpinner = (Spinner) findViewById(R.id.areaSpinner);
        areaAdapter = ArrayAdapter.createFromResource(ProEnroll.this, R.array.area, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        creditSpinner = (Spinner) findViewById(R.id.creditSpinner);
        creditAdapter = ArrayAdapter.createFromResource(ProEnroll.this, R.array.credit, android.R.layout.simple_spinner_dropdown_item);
        creditSpinner.setAdapter(creditAdapter);

        gradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        gradeAdapter = ArrayAdapter.createFromResource(ProEnroll.this, R.array.grade, android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        userNameText = (TextView) findViewById(R.id.userNameText);
        userNameText.setText(ProMain.userName);

        final EditText timeText = (EditText) findViewById(R.id.timeText);
        final EditText roomText = (EditText) findViewById(R.id.roomText);
        final EditText titleText = (EditText) findViewById(R.id.titleText);


        Button enrollBtn = (Button) findViewById(R.id.enrollBtn);
        enrollBtn.setOnClickListener(new View.OnClickListener() {

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

                boolean validate = false;
                validate = ProSchedule.validate(courseTime);
                if (validate == false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProEnroll.this);
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProEnroll.this);
                                    builder.setMessage("강의 등록에 성공하였습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProEnroll.this);
                                    builder.setMessage("강의 등록에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create()
                                            .show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ProEnrollRequest proEnrollRequest = new ProEnrollRequest(courseUniversity, courseMajor, courseYear, courseTerm, courseArea, courseGrade, courseTitle, courseCredit, courseProfessor, courseTime, courseRoom, userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProEnroll.this);
                    queue.add(proEnrollRequest);
                }
            }
        });
    }

}
