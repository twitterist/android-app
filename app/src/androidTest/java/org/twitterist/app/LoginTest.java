package org.twitterist.app;

import android.test.InstrumentationTestCase;

import com.twitter.sdk.android.core.models.User;

import org.twitterist.app.login.Profile;

/**
 * Created by marco.wuethrich on 22.09.2015.
 */
public class LoginTest extends InstrumentationTestCase {

    public void testChangeUserProfile() throws Exception {

        final User expected = Profile.getUser();
        final User reality = null;
        assertEquals(expected,reality);
    }
}
