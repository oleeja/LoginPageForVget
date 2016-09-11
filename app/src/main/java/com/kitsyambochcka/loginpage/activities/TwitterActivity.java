package com.kitsyambochcka.loginpage.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.R;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            if(isNetworkConnected()){
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(Constants.SOCIAL_NETWORKS, Constants.TWITTER);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
