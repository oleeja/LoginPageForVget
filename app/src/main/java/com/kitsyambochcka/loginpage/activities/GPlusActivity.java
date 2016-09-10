package com.kitsyambochcka.loginpage.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.squareup.picasso.Picasso;

/**
 * Created by Developer on 10.09.2016.
 */
public class GPlusActivity extends ProfileActivity implements GoogleApiClient.OnConnectionFailedListener  {


    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(getIntent());
        final GoogleSignInAccount acct = result.getSignInAccount();


        Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
            @Override
            public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
                if (loadPeopleResult.getStatus().isSuccess()) {
                    tvName.setText(acct.getDisplayName());
                    tvEmail.setText(acct.getEmail());
                    tvDateOfBirthday.setText(loadPeopleResult.getPersonBuffer().get(0).getBirthday());
                    Picasso.with(GPlusActivity.this)
                            .load(acct.getPhotoUrl())
                            .into(ivProfileImage);

                }
            }
        });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
