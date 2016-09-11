package com.kitsyambochcka.loginpage;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.kitsyambochcka.loginpage.activities.MainActivity;
import com.kitsyambochcka.loginpage.managers.DataManager;
import com.kitsyambochcka.loginpage.models.TimeSaver;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Developer on 07.09.2016.
 */
public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "spUu9K8i8XyCbO7RCOE0WIbQD";
    private static final String TWITTER_SECRET = "jUOlDN3Ezp0L4RGIJdgOCZIDpPkMmFH2AObDILSdZMvuqTGrnb";

    private static final long CLEAR_DATABASE_PERIOD =259200000; // 3 days

    private static DataManager dataManager;
    private static Context context;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(App.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);

        context = getApplicationContext();
        setupRealmDefaultInstance();

        clearDataBaseIfNeeded();
    }



    public static DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager();
            dataManager.init(getContext());
        }
        return dataManager;
    }
    private static void setupRealmDefaultInstance() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                .name(Constants.Realm.STORAGE_MAIN)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static Context getContext() {
        return context;
    }

    private void clearDataBaseIfNeeded() {
        long currentTime = System.currentTimeMillis();
        if(getDataManager().getMarker()!=null){

            long timeMarker = getDataManager().getMarker().getSavedTime();

            if(currentTime-timeMarker>CLEAR_DATABASE_PERIOD){
                getDataManager().clearDB();
                getDataManager().clearMarker();
                TimeSaver timeSaver = new TimeSaver();
                timeSaver.setSavedTime(currentTime);
                getDataManager().setTimeMarker(timeSaver);
            }
        }else {
            TimeSaver timeSaver = new TimeSaver();
            timeSaver.setSavedTime(currentTime);
            getDataManager().setTimeMarker(timeSaver);

        }

    }
}
