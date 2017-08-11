package com.layalty.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.layalty.app.MainActivity;
import com.layalty.app.R;
import com.layalty.app.activities.redeem.RedeemActivity;
import com.layalty.app.controller.MainController;
import com.layalty.app.listener.CommunicationListener;
import com.layalty.app.session.SessionManager;
import com.layalty.app.utils.AppConfig;

import org.json.JSONObject;

public class HomeActivity extends BaseDrawerActivity {

    private String TAG = HomeActivity.class.getName();

    private TextView userName, userTier, userRegDate, userPoints,
            userMob, userCity, userCountry, userAddress;

    private CommunicationListener listener = new CommunicationListener() {
        @Override
        public void onTastFinished(String response, String requestKey) {
            if (!response.isEmpty() && response != null){
                handleResponse(response);
                initResponse();
            }
        }
    };

    public static void startIntent(Activity startActivity) {

        Intent intent = new Intent(startActivity, HomeActivity.class);
        startActivity.startActivity(intent);
        startActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initResponse();
    }

    private void initView() {

        userName = (TextView) findViewById(R.id.card_username);
        userTier = (TextView) findViewById(R.id.card_usertier);
        userRegDate = (TextView) findViewById(R.id.card_userjoindate);
        userPoints = (TextView) findViewById(R.id.card_userpoints);
        userMob = (TextView) findViewById(R.id.card_userMobNo);
        userCity = (TextView) findViewById(R.id.card_userCity);
        userCountry = (TextView) findViewById(R.id.card_userCountry);
        userAddress = (TextView) findViewById(R.id.card_userAddress);

        findViewById(R.id.btn_redeem).setOnClickListener(redeemPoint);

    }

    private void handleResponse(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject member = jsonObject.getJSONObject("account");

            sessionManager.createLoginSession(member.optString("Name"),
                    member.optString("PersonEmail"));

            sessionManager.storeResponse(response);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initResponse() {
        try {
            SessionManager sessionManager = new SessionManager(HomeActivity.this);
            JSONObject object = new JSONObject(sessionManager.getKeyResponse());

            JSONObject account = object.getJSONObject("account");

            userName.setText(account.optString("Name"));

            JSONObject member = object.getJSONObject("member");
            userTier.setText("Member Tier : " + member.optString("Member_Tier__c"));
            userRegDate.setText(member.optString("Registered_date__c"));
            userPoints.setText(getResources().getString(R.string.pointBalance) + " : " + member.optString("Points__c"));
            userMob.setText(account.optString("PersonMobilePhone"));
            userCity.setText(account.optString("PersonMailingCity"));
            userCountry.setText(account.optString("PersonMailingCountry"));
            userAddress.setText(account.optString("PersonMailingStreet"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener redeemPoint = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RedeemActivity.startIntent(HomeActivity.this);
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            String url = AppConfig.URL_LOGIN + "UserName=" +
                    "m.amir@gmail.com" + "&Password=" +
                    "amir@123";
            MainController mainController = new MainController(HomeActivity.this);
            mainController.callSync(listener, url);
        }
    }
}
