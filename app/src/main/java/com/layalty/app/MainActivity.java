package com.layalty.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.layalty.app.activities.HomeActivity;
import com.layalty.app.controller.MainController;
import com.layalty.app.listener.CommunicationListener;
import com.layalty.app.session.SessionManager;
import com.layalty.app.utils.AppConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText inputUserName;
    private EditText inputPassword;

    private   SessionManager sessionManager;

    private CommunicationListener listener = new CommunicationListener() {
        @Override
        public void onTastFinished(String response, String requestKey) {
            if (!response.isEmpty() && response != null){
                handleResponse(response);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        findViewById(R.id.btn_signin).setOnClickListener(buttonClick);
    }

    private void initView() {

        sessionManager = new SessionManager(MainActivity.this);

        inputUserName = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String userName = inputUserName.getText().toString().trim();
            String uPassword = inputPassword.getText().toString().trim();

            if (!userName.isEmpty() && !uPassword.isEmpty()) {
                String url = AppConfig.URL_LOGIN + "UserName=" +
                        userName + "&Password=" +
                        uPassword;
                MainController mainController = new MainController(MainActivity.this);
                mainController.callSync(listener, url);
            } else {

            }
        }
    };

    private void handleResponse(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject member = jsonObject.getJSONObject("account");

            sessionManager.createLoginSession(member.optString("Name"),
                    member.optString("PersonEmail"));

            sessionManager.storeResponse(response);
            if (sessionManager.isLoggedIn()){
                HomeActivity.startIntent(MainActivity.this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
