package com.layalty.app.activities.coupons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.layalty.app.R;
import com.layalty.app.activities.BaseDrawerActivity;
import com.layalty.app.activities.redeem.RedeemActivity;
import com.layalty.app.adapter.CatalogueAdapter;
import com.layalty.app.adapter.CouponsAdapter;
import com.layalty.app.controller.MainController;
import com.layalty.app.listener.CommunicationListener;
import com.layalty.app.module.Coupons;
import com.layalty.app.session.SessionManager;
import com.layalty.app.utils.AppConfig;
import com.layalty.app.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CouponsActivity extends BaseDrawerActivity {

    private String memberId = null;
    private RecyclerView recyclerView;

    private CommunicationListener listener = new CommunicationListener() {
        @Override
        public void onTastFinished(String response, String requestKey) {
            Log.i("Response", response);
            initResponse(response);
        }
    };

    private void initResponse(String response){

        try {
            JSONArray jsonArray = new JSONArray(response);

            List<Coupons> couponsList = new ArrayList<>();
            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                JSONObject object = jsonArray.getJSONObject(i);

                Coupons coupons = new Coupons(object.optString("Id"),
                        object.optString("Member__c"),
                        object.optString("Expiration_Date__c"));

                couponsList.add(coupons);
            }

            CouponsAdapter adapter = new CouponsAdapter(CouponsActivity.this, couponsList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startIntent(Activity startActivity) {

        Intent intent = new Intent(startActivity, CouponsActivity.class);
        startActivity.startActivity(intent);
        startActivity.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        getSessionData();

    }

    private void getSessionData(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        SessionManager manager = new SessionManager(CouponsActivity.this);

        try {

            JSONObject object = new JSONObject(manager.getKeyResponse());

            memberId = object.getJSONObject("member").optString("Member_Number__c");

            MainController controller = new MainController(CouponsActivity.this);
            controller.callCoupons(listener, AppConfig.MY_COUPONS+memberId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
