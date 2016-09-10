package com.kitsyambochcka.loginpage.managers;

import android.content.Context;

import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.interfaces.Manager;
import com.kitsyambochcka.loginpage.models.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by Developer on 10.09.2016.
 */
public class DataManager implements Manager {
    private Realm realm;


    @Override
    public void init(Context context) {
        realm = getRealmInstance(context);

    }

    private Realm getRealmInstance(Context context) {
        try {
            return Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException exception) {
            Realm.deleteRealm(new RealmConfiguration.Builder(context)
                    .name(Constants.Realm.STORAGE_MAIN).build());
            return Realm.getDefaultInstance();
        }
    }



    public void setAccount(User user) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
        } catch (RealmException e) {
            realm.cancelTransaction();
        }
    }

    public List<User> getAllAccounts(){
        return realm.where(User.class).findAll();
    }

    public User getGPlusAccount(){
        return realm.where(User.class).equalTo(Constants.SOCIAL_NETWORKS, Constants.GPlus).findFirst();
    }

    public User getFBAccount(){
        return realm.where(User.class).equalTo(Constants.SOCIAL_NETWORKS, Constants.FACEBOOK).findFirst();
    }

    public User getVKAccount(){
        return realm.where(User.class).equalTo(Constants.SOCIAL_NETWORKS, Constants.VK).findFirst();
    }

    public User getTwitterAccount(){
        return realm.where(User.class).equalTo(Constants.SOCIAL_NETWORKS, Constants.TWITTER).findFirst();
    }
}
