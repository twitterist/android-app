package org.twitterist.app.login;

import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by marcowuthrich on 17.09.15.
 */
public class Profile {

    //Variable
    private static User user;
    private static TwitterSession session = null;
    private static String userName = null;
    private static TwitterAuthToken authToken = null;
    private static String profileImageURL;
    private static long id = 0;
    private static long userID = 0;


    //Getter / Setter


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Profile.user = user;
    }

    public static TwitterSession getSession() {
        return session;
    }

    public static void setSession(TwitterSession session) {
        Profile.session = session;
    }


    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Profile.userName = userName;
    }

    public static TwitterAuthToken getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(TwitterAuthToken authToken) {
        Profile.authToken = authToken;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        Profile.id = id;
    }

    public static long getUserID() {
        return userID;
    }

    public static void setUserID(long userID) {
        Profile.userID = userID;
    }

    public static String getProfileImageURL() {
        return profileImageURL;
    }

    public static void setProfileImageURL(String profileImageURL) {
        Profile.profileImageURL = profileImageURL;
    }
}
