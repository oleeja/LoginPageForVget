package com.kitsyambochcka.loginpage.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Developer on 11.09.2016.
 */
public class TimeSaver extends RealmObject {

    @PrimaryKey
    private long id;

    private long savedTime;

    public long getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(long savedTime) {
        this.savedTime = savedTime;
    }
}
