package com.kitsyambochcka.loginpage.activities;

import android.net.Uri;
import android.os.Bundle;

import com.kitsyambochcka.loginpage.interfaces.UserPresenter;
import com.kitsyambochcka.loginpage.models.User;
import com.kitsyambochcka.loginpage.utills.UserBuilder;
import com.squareup.picasso.Picasso;



/**
 * Created by Developer on 10.09.2016.
 */
public class TwitterActivity extends ProfileActivity implements UserPresenter {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserBuilder.createTwitterUser(this);
    }

    @Override
    public void showUserInfo(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvDateOfBirthday.setText(user.getDateOfBirthday());
        Picasso.with(TwitterActivity.this)
                .load(Uri.parse(user.getLinkPhoto()))
                .into(ivProfileImage);
    }
}
