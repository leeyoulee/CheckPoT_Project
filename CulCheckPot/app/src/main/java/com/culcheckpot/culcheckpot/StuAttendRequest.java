package com.culcheckpot.culcheckpot;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StuAttendRequest extends StringRequest {

    final static private String URL= "http://leeyoulee95.cafe24.com/AttendList.php";
    private Map<String, String> parameters;

    public StuAttendRequest(String userID, String userNum, String userName, String courseID, String StuDate, String attendText, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userNum", userNum);
        parameters.put("userName", userName);
        parameters.put("courseID", courseID);
        parameters.put("StuDate", StuDate);
        parameters.put("attendText", attendText);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
