package com.kitsyambochcka.loginpage.utills;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.kitsyambochcka.loginpage.App;
import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.interfaces.UserPresenter;
import com.kitsyambochcka.loginpage.models.User;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONObject;

/**
 * Created by Developer on 10.09.2016.
 */
public class UserBuilder {

    private static User mainUser;
    private static UserPresenter userPresenter;

    public static void createGPlusUser(final Context context, GoogleApiClient mGoogleApiClient, final GoogleSignInAccount acct){
        mainUser = new User();
        if(acct!=null){
            Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
                @Override
                public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
                    if (loadPeopleResult.getStatus().isSuccess()) {
                        mainUser.setName(acct.getDisplayName());
                        mainUser.setEmail(acct.getEmail());
                        mainUser.setDateOfBirthday(loadPeopleResult.getPersonBuffer().get(0).getBirthday());
                        mainUser.setLinkPhoto(acct.getPhotoUrl().toString());

                        mainUser.setSocialNetwork(Constants.GPlus);
                        App.getDataManager().setAccount(mainUser);

                        userPresenter = (UserPresenter)context;
                        userPresenter.showUserInfo(mainUser);
                    }
                }
            });
        }else {
            mainUser = App.getDataManager().getAccount(Constants.GPlus);
            if(mainUser!=null){
                showInfo(context,mainUser);
            }else {
                showToast(context);
            }
        }

    }

    public static void createFBUser(final Context context, final int width, final int height){
        mainUser = new User();
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            String email = object.getString(Constants.EMAIL);
                            String name = object.getString(Constants.NAME);
                            String birthday = object.getString(Constants.BIRTHDAY); // 01/31/1980 format

                            mainUser.setName(name);
                            mainUser.setEmail(email);
                            mainUser.setDateOfBirthday(birthday);
                            mainUser.setLinkPhoto(Profile.getCurrentProfile().getProfilePictureUri(width, height).toString());

                            mainUser.setSocialNetwork(Constants.FACEBOOK);
                            App.getDataManager().setAccount(mainUser);

                            userPresenter = (UserPresenter)context;
                            userPresenter.showUserInfo(mainUser);

                        } catch (Exception e) {
                            mainUser = App.getDataManager().getAccount(Constants.FACEBOOK);
                            if(mainUser!=null){
                                showInfo(context,mainUser);
                            }else {
                                showToast(context);
                            }
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(Constants.FIELDS, Constants.EMAIL+","+Constants.BIRTHDAY+","+Constants.NAME);
        request.setParameters(parameters);
        request.executeAsync();
    }

    public static void createVKUser(final Context context){

        mainUser = new User();

        VKRequest currentRequest = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, Constants.VK_PARAMETERS));


        currentRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {
                    final String email = VKAccessToken.currentToken().email;
                    JSONObject userInfo = response.json.getJSONArray(Constants.RESPONSE).getJSONObject(0);
                    String name = userInfo.getString(Constants.FIRST_NAME)+" "+userInfo.getString(Constants.LAST_NAME);

                    mainUser.setName(name);
                    mainUser.setEmail(email);
                    mainUser.setDateOfBirthday(userInfo.getString(Constants.VK_BIRTHDATE));
                    mainUser.setLinkPhoto(userInfo.getString(Constants.VK_PHOTO_MAX));

                    mainUser.setSocialNetwork(Constants.VK);
                    App.getDataManager().setAccount(mainUser);

                    showInfo(context, mainUser);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                mainUser = App.getDataManager().getVKAccount();
                if(mainUser!=null){
                    showInfo(context,mainUser);
                }else {
                    showToast(context);
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                mainUser = App.getDataManager().getVKAccount();
                if(mainUser!=null){
                    showInfo(context,mainUser);
                }else {
                    showToast(context);
                }
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }

        });
    }

    public static void createTwitterUser(final Context context){
        mainUser = new User();

        final TwitterAuthClient authClient;
        final TwitterSession session;
        authClient = new TwitterAuthClient();
        session = Twitter.getSessionManager().getActiveSession();

        if(session!=null){
            Twitter.getApiClient(session).getAccountService()
                    .verifyCredentials(true, false).enqueue(new Callback<com.twitter.sdk.android.core.models.User>() {

                @Override
                public void failure(TwitterException e) {
                    mainUser = App.getDataManager().getAccount(Constants.TWITTER);
                    if(mainUser!=null){
                        showInfo(context,mainUser);
                    }else {
                        showToast(context);
                    }
                }

                @Override
                public void success(Result<com.twitter.sdk.android.core.models.User> userResult) {
                    com.twitter.sdk.android.core.models.User user = userResult.data;

                    String profileImage = user.profileImageUrl.replace(Constants.TWITTER_NORMAL, Constants.EMPTY_STRING);

                    mainUser.setName(user.name);
                    mainUser.setDateOfBirthday(Constants.EMPTY_STRING);//Twitter api doesn't resolve to get date of birthday
                    mainUser.setLinkPhoto(profileImage);

                    authClient.requestEmail(session, new Callback<String>() {
                        @Override
                        public void success(Result<String> stringResult) {
                            showTwitterInfo(stringResult.data, context);
                        }

                        @Override
                        public void failure(TwitterException e) {
                            showTwitterInfo(Constants.EMPTY_STRING, context);
                        }
                    });

                }
            });
        }else {
            mainUser = App.getDataManager().getAccount(Constants.TWITTER);
            if(mainUser!=null){
                showInfo(context,mainUser);
            }else {
                showToast(context);
            }
        }

    }

    public static void showTwitterInfo(String email, Context context){
        mainUser.setSocialNetwork(Constants.TWITTER);
        App.getDataManager().setAccount(mainUser);
        mainUser.setEmail(email);
        userPresenter = (UserPresenter)context;
        userPresenter.showUserInfo(mainUser);
    }



    public static void showInfo(Context context, User showingUser){
        userPresenter = (UserPresenter)context;
        userPresenter.showUserInfo(showingUser);
    }

    public static void showToast(Context context) {
        Toast.makeText(context, "Error: Interner connection", Toast.LENGTH_SHORT).show();
    }

}
