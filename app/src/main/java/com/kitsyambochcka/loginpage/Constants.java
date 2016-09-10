package com.kitsyambochcka.loginpage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Developer on 09.09.2016.
 */
public class Constants {
    public static final String ACCESS_TOCKEN = "ACCESS_TOCKEN";
    public static final String EMAIL = "email";
    public static final String BIRTHDAY = "birthday";
    public static final String NAME = "name";
    public static final String FIELDS = "fields";
    public static final String COUNT = "count";
    public static final String ALL_PHOTOS = "photos.getAll";
    public static final String RESPONSE = "response";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String VK_BIRTHDATE = "bdate";
    public static final String VK_PHOTO_MAX = "photo_max_orig";
    public static final String VK_PARAMETERS = VK_PHOTO_MAX+","+FIRST_NAME+","+LAST_NAME+","+VK_BIRTHDATE;
    public static final String EMPTY_STRING = "";
    public static final String TWITTER_NORMAL = "_normal";

    public static List<String> fbPermissions(){
        return Arrays.asList("public_profile", "email", "user_birthday", "user_friends");
    }
}
