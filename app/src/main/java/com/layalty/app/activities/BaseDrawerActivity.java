package com.layalty.app.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.layalty.app.BaseActivity;
import com.layalty.app.R;
import com.layalty.app.customwidgets.RoundedImageView;
import com.layalty.app.session.SessionManager;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Miroslaw Stanek on 15.07.15.
 */
public class BaseDrawerActivity extends BaseActivity {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vNavigation)
    NavigationView vNavigation;

    SessionManager sessionManager;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);

        sessionManager = new SessionManager(BaseDrawerActivity.this);
        bindViews();
        setupHeader();
        setUpNavigationView();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    public void setupHeader() {
        View headerView = vNavigation.getHeaderView(0);
        TextView userName = (TextView) headerView.findViewById(R.id.userName);
        TextView userEmail = (TextView) headerView.findViewById(R.id.userEmail);

        HashMap<String, String> userDetails = sessionManager.getUserDetails();
        userName.setText(userDetails.get(SessionManager.KEY_NAME));
        userEmail.setText(userDetails.get(SessionManager.KEY_EMAIL));

        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });

        String strBase64 = "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" +
                "+2dvhrfmfINmmo9ASrl5lntvcYWBN0DouXIsVCho6JIHRcuRYUMcB0TCwdFy5OworXVMIJdUsrly0xvczmV9CYaYXLl0GZ//Z";
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        RoundedImageView imageView = (RoundedImageView) headerView.findViewById(R.id.ivMenuUserProfilePhoto);
        imageView.setImageBitmap(decodedByte);
    }

    public void onGlobalMenuHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                overridePendingTransition(0, 0);
            }
        }, 200);
    }

    private void setUpNavigationView() {

        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_home:
                        //UserProfileActivity.startUserProfileFromLocation(BaseDrawerActivity.this);
                        return true;

                    case R.id.menu_logout:

                        sessionManager.logoutUser();
                        return true;

                }
                return true;
            }
        });
    }

}
