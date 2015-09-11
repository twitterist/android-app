package app.twitterist.org.twitterist.login;

import android.content.SharedPreferences;
import android.net.Uri;

import twitter4j.Twitter;

/**
 * Created by marcowuthrich on 09.09.15.
 */
public class UserData {

    private static Uri uri;

    private static Twitter twitter;
    private static String userID;
    private static String username = "";
    private static boolean login;
    private static SharedPreferences mSharedPreferences;

    //Default Construktor
    public UserData() {
    }

    public static Uri getUri() {
        return uri;
    }

    public static void setUri(Uri uri) {
        UserData.uri = uri;
    }

    public static Twitter getTwitter() {
        return twitter;
    }

    public static void setTwitter(Twitter twitter) {
        UserData.twitter = twitter;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        UserData.userID = userID;
    }

    public static boolean isLogin() {
        return login;
    }

    public static void setLogin(boolean login) {
        UserData.login = login;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserData.username = username;
    }

    public static SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }

    public static void setmSharedPreferences(SharedPreferences mSharedPreferences) {
        UserData.mSharedPreferences = mSharedPreferences;
    }
}
