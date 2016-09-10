package com.kitsyambochcka.loginpage.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Developer on 09.09.2016.
 */
public class VKActivity extends ProfileActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bGoToGallery.setVisibility(View.VISIBLE);
        final String email = VKAccessToken.currentToken().email;

        VKRequest currentRequest = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max_orig,first_name,last_name,bdate"));

        showProgressDialog();
        currentRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.d("VkDemoApp", "onComplete " + response);
                Log.d("VkDemoApp", "onComplete " + response.responseString);


                try {
                    JSONObject userInfo = response.json.getJSONArray("response").getJSONObject(0);
                    String name = userInfo.getString("first_name")+" "+userInfo.getString("last_name");
                    tvName.setText(name);
                    tvEmail.setText(email);
                    tvDateOfBirthday.setText(userInfo.getString("bdate"));
                    Picasso.with(VKActivity.this)
                            .load(Uri.parse(userInfo.getString("photo_max_orig")))
                            .into(ivProfileImage);
                    hideProgressDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Log.d("VkDemoApp", "attemptFailed " + request + " " + attemptNumber + " " + totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d("VkDemoApp", "onError: " + error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d("VkDemoApp", "onProgress " + progressType + " " + bytesLoaded + " " + bytesTotal);
            }
        });

        bGoToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VKActivity.this, GalleryActivity.class));
            }
        });
    }
}
