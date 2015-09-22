package org.twitterist.app.model;

import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by marcowuthrich on 17.09.15.
 */
public class Profile {

    //Variable
    private static User user = null;
    private static TwitterSession session = null;

    //Default Constructor
    public Profile() {
    }

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

}
