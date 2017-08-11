package com.layalty.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.layalty.app.R;
import com.layalty.app.activities.redeem.RedeemActivity;
import com.layalty.app.customwidgets.CustomDialogClass;
import com.layalty.app.module.RedeemCatalogue;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by 19569 on 6/29/2017.
 */

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {

    private List<RedeemCatalogue> catalogueList = null;
    private Context context = null;

    public CatalogueAdapter(Context context, List<RedeemCatalogue> catalogueList){
    this.context = context;
        this.catalogueList = catalogueList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_catalogue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(catalogueList.get(position).getName());
        holder.points.setText("Points : "+catalogueList.get(position).getPoints__c());

        Picasso.with(context).
                load(catalogueList.get(position).getImage_URL__c()).
                into(holder.image);

        holder.redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* */
                final CustomDialogClass dialogClass = new CustomDialogClass((Activity) context);
                dialogClass.show();
                dialogClass.setTextValue(context.getString(R.string.redeeem_alert));
                dialogClass.yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RedeemActivity.getInstance().redeemCatalogue(catalogueList.get(position));
                        dialogClass.dismiss();
                    }
                });
                dialogClass.no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClass.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return catalogueList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, points;
        private ImageView image;
        private Button redeem;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.catalogueName);
            image = (ImageView) itemView.findViewById(R.id.catalogueImage);
            points = (TextView) itemView.findViewById(R.id.cataloguePoints);
            redeem = (Button) itemView.findViewById(R.id.redeemButton);
        }
    }
}
