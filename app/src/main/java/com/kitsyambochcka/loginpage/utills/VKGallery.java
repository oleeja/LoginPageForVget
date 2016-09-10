package com.kitsyambochcka.loginpage.utills;

import android.content.Context;

import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.interfaces.GalleryUpdater;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;

import java.util.ArrayList;

/**
 * Created by Developer on 10.09.2016.
 */
public class VKGallery {
    //max = 200 photos (vk documentation)
    private static int PHOTO_COUNT = 50;

    public static ArrayList<String> getPhotosUrl(final Context context){
        final ArrayList<String> uriPhoto = new ArrayList<>();
        VKRequest requestPhoto = new VKRequest(Constants.ALL_PHOTOS, VKParameters.from(VKApiConst.OWNER_ID,
                VKAccessToken.currentToken().userId, Constants.COUNT, PHOTO_COUNT), VKPhotoArray.class);
        requestPhoto.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKPhotoArray photoArray = (VKPhotoArray) response.parsedModel;

                for (VKApiPhoto photoFull : photoArray) {
                    uriPhoto.add(photoFull.photo_604);
                }

                GalleryUpdater galleryUpdater = (GalleryUpdater)context;
                galleryUpdater.notifyAdapter();

            }
        });
        return uriPhoto;
    }
}
