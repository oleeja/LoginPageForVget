package com.kitsyambochcka.loginpage.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitsyambochcka.loginpage.R;

import butterknife.BindView;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.profile_image)ImageView ivProfileImage;
    @BindView(R.id.textViewName)TextView tvName;
    @BindView(R.id.textViewEmail)TextView tvEmail;
    @BindView(R.id.textViewDateOfBirthday)TextView tvDateOfBirthday;
    @BindView(R.id.pagerGallery)ViewPager vpGallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
