package com.culcheckpot.culcheckpot;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StuEnrollRequest extends StringRequest {

    final static private String URL= "http://leeyoulee95.cafe24.com/CourseAdd.php";
    private Map<String, String> parameters;

    public StuEnrollRequest(String userID, String courseID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
