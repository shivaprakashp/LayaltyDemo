package com.layalty.app.activities.redeem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.layalty.app.R;
import com.layalty.app.activities.BaseDrawerActivity;
import com.layalty.app.activities.HomeActivity;
import com.layalty.app.adapter.CatalogueAdapter;
import com.layalty.app.constants.AppConstants;
import com.layalty.app.controller.MainController;
import com.layalty.app.customwidgets.CustomDialogClass;
import com.layalty.app.listener.CommunicationListener;
import com.layalty.app.module.RedeemCatalogue;
import com.layalty.app.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RedeemActivity extends BaseDrawerActivity {

    private String TAG = RedeemActivity.class.getName();

    private RecyclerView recyclerView;
    private static RedeemActivity activity;
    private SessionManager manager;
    private JSONObject member;
    private CustomDialogClass dialogClass;
    private TextView points;

    public static void startIntent(Activity startActivity) {

        Intent intent = new Intent(startActivity, RedeemActivity.class);
        //startActivity.startActivity(intent);
        startActivity.startActivityForResult(intent, 2);
        //startActivity.finish();
    }

    private CommunicationListener listener = new CommunicationListener() {
        @Override
        public void onTastFinished(String response, String requestKey) {
            if (requestKey.equals(AppConstants.CATALOGUE.REDEEM_CATALOGUE)) {
                prepareJson(response);
            } else if (requestKey.equals(AppConstants.REDEEMNOW.REDEEM_NOW)) {
                if (!response.isEmpty()) {
                    Log.i("Tag", response.toString());
                    callLogin(response);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        initData();
        callRedeem();
    }

    private void initData() {
        manager = new SessionManager(RedeemActivity.this);

        TextView uName = (TextView) findViewById(R.id.redeem_card_username);
        points = (TextView) findViewById(R.id.redeem_card_userpoints);

        try {

            JSONObject object = new JSONObject(manager.getKeyResponse());
            JSONObject account = object.getJSONObject("account");

            uName.setText(account.optString("Name"));
            member = object.getJSONObject("member");
            updatePointBalance(member.optString("Points__c"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updatePointBalance(String balance) {
        points.setText(getResources().getString(R.string.pointBalance) + " : " + balance);
    }

    private void callRedeem() {

        activity = this;

        MainController controller = new MainController(RedeemActivity.this);
        controller.callRedeemCatalouge(listener);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    public static RedeemActivity getInstance() {
        return activity;
    }

    private void prepareJson(String response) {

        Log.i(TAG, response);

        try {

            List<RedeemCatalogue> catalogueList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                RedeemCatalogue catalogue = new RedeemCatalogue(object.optString("Name"),
                        object.optString("Description__c"),
                        object.optString("Points__c"), object.optString("Image_URL__c"),
                        object.optString("Reward_Product_Name__c"));

                catalogueList.add(catalogue);
            }

            CatalogueAdapter adapter = new CatalogueAdapter(RedeemActivity.this, catalogueList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void redeemCatalogue(RedeemCatalogue points) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        JSONObject object = new JSONObject();
        try {
            object.put("MemberUsername", member.optString("Member_Number__c"));
            object.put("MemberActivityType", "Redemption");
            object.put("Points", points.getPoints__c());
            object.put("ProcessingMode", "Realtime");
            object.put("ActivityDate", dateFormat.format(date));
            object.put("Source", "Web");
            object.put("Description__c", points.getDescription__c());

            Log.i("JsonObject", object.toString());

            MainController controller = new MainController(RedeemActivity.this);
            controller.callRedeemItem(listener, "POST", object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callLogin(String response) {
        dialogClass = new CustomDialogClass(RedeemActivity.this);
        if (response.contains("500")) {
            dialogClass.show();
            dialogClass.setTextValue(response);
        } else {
            dialogClass.show();
            dialogClass.setTextValue("Total Point Balance :" + response);
            updatePointBalance(response);
        }

        dialogClass.yes.setOnClickListener(dismissDialog);
        dialogClass.no.setOnClickListener(dismissDialog);
        //Intent intent = new Intent();
        //setResult(2, intent);
        //RedeemActivity.this.finish();
    }

    private View.OnClickListener dismissDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogClass.dismiss();
        }
    };
}
