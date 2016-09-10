package com.kitsyambochcka.loginpage.activities;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Developer on 09.09.2016.
 */
public class FBActivity extends ProfileActivity {
//    private ProfileTracker mProfileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showProgressDialog();
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        try {
                            String email = object.getString("email");
                            String birthday = object.getString("birthday"); // 01/31/1980 format

                            tvEmail.setText(email);
                            tvDateOfBirthday.setText(birthday);

                            tvName.setText(Profile.getCurrentProfile().getName());
                            Picasso.with(FBActivity.this)
                                    .load(Profile.getCurrentProfile().getProfilePictureUri(ivProfileImage.getWidth(), ivProfileImage.getHeight()))
                                    .into(ivProfileImage);

                            hideProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }
}
