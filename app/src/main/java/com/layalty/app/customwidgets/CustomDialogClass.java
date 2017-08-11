package com.layalty.app.customwidgets;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.layalty.app.R;
import com.layalty.app.activities.redeem.RedeemActivity;

/**
 * Created by 19569 on 7/17/2017.
 */

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private boolean flag = false;
    public TextView textView;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        textView = (TextView) findViewById(R.id.txt_dia);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    public void setTextValue(String text){
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                flag = true;
                dismiss();
                break;
            case R.id.btn_no:
                flag = false;
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public boolean isFlag(){
        return flag;
    }
}
