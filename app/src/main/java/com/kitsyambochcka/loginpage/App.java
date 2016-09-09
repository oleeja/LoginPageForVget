package com.kitsyambochcka.loginpage;

import android.app.Application;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.vk.sdk.VKSdk;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Developer on 07.09.2016.
 */
public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "spUu9K8i8XyCbO7RCOE0WIbQD";
    private static final String TWITTER_SECRET = "jUOlDN3Ezp0L4RGIJdgOCZIDpPkMmFH2AObDILSdZMvuqTGrnb";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        VKSdk.initialize(this);
    }


}
