package com.culcheckpot.culcheckpot;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProDeleteRequest extends StringRequest {

    final static private String URL= "http://leeyoulee95.cafe24.com/ProDelete.php";
    private Map<String, String> parameters;

    public ProDeleteRequest(String userID, String courseID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);

        Log.d("test",userID + " " + courseID );
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
