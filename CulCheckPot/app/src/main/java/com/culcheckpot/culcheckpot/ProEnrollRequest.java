package com.culcheckpot.culcheckpot;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.culcheckpot.culcheckpot.ProMain.userName;

public class ProEnrollRequest extends StringRequest {

    final static private String URL= "http://leeyoulee95.cafe24.com/professorenrollment.php";
    private Map<String, String> parameters;

    public ProEnrollRequest(String courseUniversity, String courseMajor, String courseYear, String courseTerm,
                            String courseArea, String courseGrade, String courseTitle, String courseCredit, String courseProfessor, String courseTime, String courseRoom, String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("courseUniversity", courseUniversity);
        parameters.put("courseMajor", courseMajor);
        parameters.put("courseYear", courseYear);
        parameters.put("courseTerm", courseTerm);
        parameters.put("courseArea", courseArea);
        parameters.put("courseGrade", courseGrade);
        parameters.put("courseTitle", courseTitle);
        parameters.put("courseCredit", courseCredit);
        parameters.put("courseProfessor", courseProfessor);
        parameters.put("courseTime", courseTime);
        parameters.put("courseRoom", courseRoom);
        parameters.put("userID", userID);

        Log.v("testlog "," "+courseUniversity + " "+ courseMajor + " "+ courseYear + " "+courseTerm + " "+ courseArea+ " "+ courseGrade+ " "+ courseTitle+ " "+ courseCredit+ " "+ courseProfessor+ " "+ courseTime+ " "+ courseRoom+ " "+ userID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
