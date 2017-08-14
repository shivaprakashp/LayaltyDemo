package com.layalty.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.layalty.app.R;
import com.layalty.app.activities.redeem.RedeemActivity;
import com.layalty.app.customwidgets.CustomDialogClass;
import com.layalty.app.module.Coupons;
import com.layalty.app.module.RedeemCatalogue;
import com.layalty.app.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 19569 on 6/29/2017.
 */

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.ViewHolder> {

    private List<Coupons> couponsList = null;
    private Context context = null;

    public CouponsAdapter(Context context, List<Coupons> couponsList){
    this.context = context;
        this.couponsList = couponsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.coupons_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.couponDesc.setText("Member "+couponsList.get(position).getCouponDesc());
        holder.couponExpire.setText("Expires on : "+couponsList.get(position).getCouponExpire());
        holder.couponId.setText(couponsList.get(position).getCouponId());

        Bitmap bitmap = AppUtils.generateQR(couponsList.get(position).getCouponId());
        holder.image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return couponsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView couponId, couponDesc, couponExpire;
        private ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);

            couponId = (TextView) itemView.findViewById(R.id.coupon_no);
            image = (ImageView) itemView.findViewById(R.id.qr_image);
            couponDesc = (TextView) itemView.findViewById(R.id.coupon_describe);
            couponExpire = (TextView) itemView.findViewById(R.id.coupon_expire);

        }
    }
}
