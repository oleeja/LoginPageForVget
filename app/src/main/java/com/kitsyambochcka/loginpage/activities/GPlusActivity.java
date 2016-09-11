package com.kitsyambochcka.loginpage.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.kitsyambochcka.loginpage.interfaces.UserPresenter;
import com.kitsyambochcka.loginpage.models.User;
import com.kitsyambochcka.loginpage.utills.UserBuilder;
import com.squareup.picasso.Picasso;

/**
 * Created by Developer on 10.09.2016.
 */
public class GPlusActivity extends ProfileActivity implements GoogleApiClient.OnConnectionFailedListener,UserPresenter {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(getIntent());
        final GoogleSignInAccount acct = result.getSignInAccount();



        UserBuilder.createGPlusUser(this, mGoogleApiClient,acct);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void showUserInfo(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvDateOfBirthday.setText(user.getDateOfBirthday());
        Picasso.with(GPlusActivity.this)
                .load(Uri.parse(user.getLinkPhoto()))
                .into(ivProfileImage);


    }
}
