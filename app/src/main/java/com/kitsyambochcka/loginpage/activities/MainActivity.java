package com.kitsyambochcka.loginpage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener{

    @BindView(R.id.sign_in_facebook)LoginButton facebookLoginButton;
    @BindView(R.id.sign_in_vk) ImageButton vkLoginButton;
    @BindView(R.id.sign_in_google)SignInButton gPlusLoginButton;
    @BindView(R.id.sign_in_twitter)TwitterLoginButton twitterLoginButton;
    private CallbackManager callbackManager;
    private String[] scope = new String[]{VKScope.PHOTOS, VKScope.EMAIL};
    private static final int RC_SIGN_IN = 1234;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton.setReadPermissions(Constants.fbPermissions());


        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        startActivity(new Intent(MainActivity.this, FBActivity.class));

                    }

                    @Override
                    public void onCancel() {
                        //TODO: handle cancel occurs while login
                    }

                    @Override
                    public void onError(FacebookException error) {
                        startActivity(new Intent(MainActivity.this, FBActivity.class));
                    }
                });
            }
        });


        vkLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(MainActivity.this,scope);
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        gPlusLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                startActivity(new Intent(MainActivity.this, TwitterActivity.class));

            }

            @Override
            public void failure(TwitterException exception) {
                startActivity(new Intent(MainActivity.this, TwitterActivity.class));

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {


                startActivity(new Intent(MainActivity.this, VKActivity.class));
            }

            @Override
            public void onError(VKError error) {
                startActivity(new Intent(MainActivity.this, VKActivity.class));
                //TODO: handle failure occurs while login
            }
        }));

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            data.setClass(this, GPlusActivity.class);
            startActivity(data);
        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
