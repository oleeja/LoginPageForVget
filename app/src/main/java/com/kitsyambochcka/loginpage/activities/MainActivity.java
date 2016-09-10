package com.kitsyambochcka.loginpage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.Arrays;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.sign_in_facebook)LoginButton facebookLoginButton;
    @BindView(R.id.sign_in_vk) ImageButton vkLoginButton;
    private CallbackManager callbackManager;
    private String[] scope = new String[]{VKScope.PHOTOS, VKScope.EMAIL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        facebookLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {
                Intent intent = new Intent(MainActivity.this, FBActivity.class);
                intent.putExtra(Constants.ACCESS_TOCKEN, loginResult.getAccessToken());
                startActivity(intent);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        vkLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(MainActivity.this,scope);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                Intent intent = new Intent(MainActivity.this, VKActivity.class);
                intent.putExtra(Constants.ACCESS_TOCKEN, res.accessToken);
                startActivity(intent);
                Log.d("MyTag", res.email);
                Log.d("MyTag", res.userId);
            }

            @Override
            public void onError(VKError error) {
            }
        }));

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);



    }
}
