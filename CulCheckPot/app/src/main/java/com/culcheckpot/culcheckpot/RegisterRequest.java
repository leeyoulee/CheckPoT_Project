package com.culcheckpot.culcheckpot;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL= "http://leeyoulee95.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userJob, String userNum, String userUniversity, String userMajor, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userJob", userJob);
        parameters.put("userNum", userNum);
        parameters.put("userUniversity", userUniversity);
        parameters.put("userMajor", userMajor);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
