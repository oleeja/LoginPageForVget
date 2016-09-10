package com.kitsyambochcka.loginpage.activities;

import android.os.Bundle;
import android.util.Log;

import com.kitsyambochcka.loginpage.R;
import com.kitsyambochcka.loginpage.activities.BaseActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;

public class GalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gallery);
        super.onCreate(savedInstanceState);

        VKRequest requestPhoto = new VKRequest("photos.getAll", VKParameters.from(VKApiConst.OWNER_ID,
                VKAccessToken.currentToken().userId, "count", 50), VKPhotoArray.class);
        requestPhoto.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKPhotoArray avataraArray = (VKPhotoArray) response.parsedModel;

                for (VKApiPhoto avatarkaFull : avataraArray) {

                    Log.d("MyTag", avatarkaFull.photo_604);
//                    new DownloadImageTask((ImageView) findViewById(R.id.imageView3))
//                            .execute(avatarkaFull.photo_130);
                }
            }
        });
    }


}
