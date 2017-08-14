package com.layalty.app.controller;

import android.content.Context;

import com.layalty.app.constants.AppConstants;
import com.layalty.app.listener.CommunicationListener;
import com.layalty.app.module.RequestProperties;
import com.layalty.app.utils.AppConfig;
import com.layalty.app.utils.HttpRequest;

/**
 * Created by 19569 on 6/19/2017.
 */

public class MainController {

    private Context context;
    public MainController(Context context){
        this.context = context;
    }

    public void callSync(CommunicationListener listener, String url ){
        RequestProperties properties = new RequestProperties();
        properties.setRequestKey(AppConstants.LOGIN.LOGIN);
        HttpRequest httpRequest = new HttpRequest(context, listener, "GET", null, properties);
        httpRequest.execute(url);
    }

    public void callCoupons(CommunicationListener listener, String url){
        RequestProperties properties = new RequestProperties();
        properties.setRequestKey(AppConstants.COUPONS.COUPONS);
        HttpRequest httpRequest = new HttpRequest(context, listener, "GET", null, properties);
        httpRequest.execute(url);
    }

    public void callRedeemCatalouge( CommunicationListener listener){
        RequestProperties properties = new RequestProperties();
        properties.setRequestKey(AppConstants.CATALOGUE.REDEEM_CATALOGUE);
        HttpRequest httpRequest = new HttpRequest(context, listener, "GET", null,
                properties);
        httpRequest.execute(AppConfig.REDEEM_CATALOGUE);
    }

    public void callRedeemItem( CommunicationListener listener, String post, String jsonData){
        RequestProperties properties = new RequestProperties();
        properties.setRequestKey(AppConstants.REDEEMNOW.REDEEM_NOW);
        HttpRequest httpRequest = new HttpRequest(context, listener, post, jsonData, properties);
        httpRequest.execute(AppConfig.REDEEM_MY_POINT);
    }
}
