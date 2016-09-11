package com.kitsyambochcka.loginpage.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.R;
import com.kitsyambochcka.loginpage.adaprers.ImagePagerAdapter;
import com.kitsyambochcka.loginpage.interfaces.GalleryUpdater;
import com.kitsyambochcka.loginpage.interfaces.UserPresenter;
import com.kitsyambochcka.loginpage.models.User;
import com.kitsyambochcka.loginpage.utills.UserBuilder;
import com.kitsyambochcka.loginpage.utills.VKGallery;
import com.squareup.picasso.Picasso;

/**
 * Created by Developer on 09.09.2016.
 */
public class VKActivity extends ProfileActivity implements UserPresenter,GalleryUpdater {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vpGallery.setVisibility(View.VISIBLE);

        UserBuilder.createVKUser(this);

        vpGallery.setAdapter(new ImagePagerAdapter(VKGallery.getPhotosUrl(this)));


    }


    @Override
    public void showUserInfo(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvDateOfBirthday.setText(user.getDateOfBirthday());
        Picasso.with(VKActivity.this)
                .load(Uri.parse(user.getLinkPhoto()))
                .into(ivProfileImage);
    }

    @Override
    public void notifyAdapter() {
        vpGallery.getAdapter().notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            if(isNetworkConnected()){
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(Constants.SOCIAL_NETWORKS, Constants.VK);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}