package com.culcheckpot.culcheckpot;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProAttendRequest extends StringRequest {

    final static private String URL= "http://leeyoulee95.cafe24.com/ProAttend.php";
    private Map<String, String> parameters;

    public ProAttendRequest(String userID, String courseID, String ProDate, String ProTime, String ProLat, String ProLng, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
        parameters.put("ProDate", ProDate);
        parameters.put("ProTime", ProTime);
        parameters.put("ProLat", ProLat);
        parameters.put("ProLng", ProLng);

        Log.v("test"," "+userID + " "+ courseID + " " + ProDate + " " + ProTime + " " +ProLat + " " +ProLng);

    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
