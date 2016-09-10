package com.kitsyambochcka.loginpage.activities;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.kitsyambochcka.loginpage.interfaces.UserPresenter;
import com.kitsyambochcka.loginpage.models.User;
import com.kitsyambochcka.loginpage.utills.UserBuilder;
import com.squareup.picasso.Picasso;

/**
 * Created by Developer on 09.09.2016.
 */
public class FBActivity extends ProfileActivity implements UserPresenter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

            UserBuilder.createFBUser(this, ivProfileImage.getWidth(), ivProfileImage.getHeight());

    }

    @Override
    public void showUserInfo(User user) {
        tvEmail.setText(user.getEmail());
        tvDateOfBirthday.setText(user.getDateOfBirthday());

        tvName.setText(user.getName());
        Picasso.with(FBActivity.this)
                .load(Uri.parse(user.getLinkPhoto()))
                .into(ivProfileImage);

    }

    @Override
    protected void onDestroy() {
        LoginManager.getInstance().logOut();
        super.onDestroy();
    }
}
