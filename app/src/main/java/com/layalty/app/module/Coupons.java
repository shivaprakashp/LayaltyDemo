package com.layalty.app.module;

/**
 * Created by 19569 on 8/14/2017.
 */

public class Coupons {

    private String couponId;
    private String couponDesc;
    private String couponExpire;


    public Coupons(String couponId, String couponDesc, String couponExpire){
        this.couponId = couponId;
        this.couponDesc = couponDesc;
        this.couponExpire = couponExpire;
    }

    public String getCouponId() {
        return couponId;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public String getCouponExpire() {
        return couponExpire;
    }

}
