package com.kitsyambochcka.loginpage.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitsyambochcka.loginpage.R;
import com.kitsyambochcka.loginpage.activities.BaseActivity;

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
}
