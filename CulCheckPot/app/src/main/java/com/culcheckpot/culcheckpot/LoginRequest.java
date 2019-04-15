package com.culcheckpot.culcheckpot;

        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL= "http://leeyoulee95.cafe24.com/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userId, String userPw, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("userPw", userPw);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
