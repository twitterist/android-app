package org.twitterist.app.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.twitterist.app.Controller;
import org.twitterist.app.R;
import org.twitterist.app.drawer.DrawerMain;

public class LoginActivity extends DrawerMain {

    Controller controller;
    TwitterLoginButton loginButton;
    ImageView twitterImageProfile;
    TextView profileUserName;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.activity_login, null ,false);
        controller.setCurrentView(mView);
        mDrawerLayout.addView(mView, 0);
        twitterImageProfile = (ImageView) mView.findViewById(R.id.imageView_user_profile);
        profileUserName = (TextView) mView.findViewById(R.id.profileUserName);

        loginButton = (TwitterLoginButton) mView.findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //Save UserDate in Profile
                TwitterSession session = result.data;
                Profile.setUserName(session.getUserName());
                Profile.setAuthToken(session.getAuthToken());
                Profile.setUserID(session.getUserId());
                Profile.setId(session.getId());

                //Twitter Client
                Twitter.getApiClient(session).getAccountService()
                        .verifyCredentials(true, false, new Callback<User>() {

                            @Override
                            public void failure(TwitterException e) {

                            }

                            @Override
                            public void success(Result<User> userResult) {
                                User user = userResult.data;
                                Profile.setProfileImageURL(user.profileImageUrl);
                                changeUserProfile();
                            }

                        });


            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeUserProfile(){

        //Load Image
        if (Profile.getProfileImageURL() != null){
            Picasso.with(getApplicationContext())
                    .load(Profile.getProfileImageURL())
                    .transform(new CircleTransform())
                    .into(twitterImageProfile);
                    twitterImageProfile.setBackgroundResource(R.drawable.circle_transparent_userimg);
        }
        //Set UserName
        if (Profile.getUserName() != null){
            profileUserName.setText("Willkommen "+Profile.getUserName());
        }
    }




}
