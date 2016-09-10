package com.kitsyambochcka.loginpage.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;


/**
 * Created by Developer on 10.09.2016.
 */
public class TwitterActivity extends ProfileActivity {

    private TwitterAuthClient authClient;
    private TwitterSession session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         authClient = new TwitterAuthClient();

         session = Twitter.getSessionManager().getActiveSession();

        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false).enqueue(new Callback<User>() {

            @Override
            public void failure(TwitterException e) {

            }

            @Override
            public void success(Result<User> userResult) {
                User user = userResult.data;

                String profileImage = user.profileImageUrl.replace("_normal", "");
                setEmail();

                tvName.setText(user.name);
                Picasso.with(TwitterActivity.this)
                        .load(profileImage)
                        .into(ivProfileImage);

            }
        });


    }

    private void setEmail() {

        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> stringResult) {

                tvEmail.setText(stringResult.data);

            }

            @Override
            public void failure(TwitterException e) {

                Log.d("MyTag", e.toString());
            }
        });
    }

}
