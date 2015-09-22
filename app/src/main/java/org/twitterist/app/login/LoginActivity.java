package org.twitterist.app.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
import org.twitterist.app.aboutUs.AboutUsActivity;
import org.twitterist.app.analysis.AnalysisActivity;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.twitter.TwitterActivity;

public class LoginActivity extends DrawerMain {

    Controller controller;
    TwitterLoginButton loginButton;
    ImageButton imageButtonTwitter, imageButtonAboutUs, imageButtonAnalysis;
    ImageView twitterImageProfile;
    TextView txtViewProfileName, txtViewTwitter, txtViewAnalysis, txtViewAboutUs ;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mView = inflater.inflate(R.layout.activity_login, null ,false);
        controller.setCurrentView(mView);
        mDrawerLayout.addView(mView, 0);
        //UI
        twitterImageProfile = (ImageView) mView.findViewById(R.id.imageView_user_profile);
        txtViewProfileName = (TextView) mView.findViewById(R.id.txtViewProfileName);
        loginButton = (TwitterLoginButton) mView.findViewById(R.id.twitter_login_button);
        imageButtonTwitter = (ImageButton) mView.findViewById(R.id.imageButton_twitter_login_Zone);
        imageButtonAboutUs = (ImageButton) mView.findViewById(R.id.imageButton_AboutUS_Icon_Login_Zone);
        imageButtonAnalysis = (ImageButton) mView.findViewById(R.id.imageButton_Analysis_Icon_Login_Zone);
        txtViewTwitter = (TextView) mView.findViewById(R.id.textViewTwitter);
        txtViewAnalysis = (TextView) mView.findViewById(R.id.textViewAnalysis);
        txtViewAboutUs = (TextView) mView.findViewById(R.id.textViewAboutUs);


        if (Profile.getUser() == null){
            initNotLogin();
        }else {
            initIsLogin();
        }
        //listener
        twitterImageListener(imageButtonTwitter,mView);
        analysisImageListener(imageButtonAnalysis,mView);
        aboutUsImageListener(imageButtonAboutUs, mView);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void initNotLogin(){


        txtViewAboutUs.setVisibility(View.GONE);
        txtViewAnalysis.setVisibility(View.GONE);
        txtViewTwitter.setVisibility(View.GONE);


        imageButtonAboutUs.setVisibility(View.GONE);
        imageButtonAnalysis.setVisibility(View.GONE);
        imageButtonTwitter.setVisibility(View.GONE);
        //Login
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //Save Session in Profile
                Profile.setSession(result.data);

                //Twitter Client
                Twitter.getApiClient(Profile.getSession()).getAccountService()
                        .verifyCredentials(true, false, new Callback<User>() {

                            @Override
                            public void failure(TwitterException e) {
                                Log.e("Login", e.getMessage());
                            }

                            @Override
                            public void success(Result<User> userResult) {
                                //Save User
                                Profile.setUser(userResult.data);
                                //Change to Profile
                                changeUserProfile();
                            }
                        });
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Login", exception.getMessage());
            }
        });
    }

    public void initIsLogin(){
        changeUserProfile();


    }


    public void twitterImageListener(ImageButton imageButton, final View mView){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mView.getContext(), TwitterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void analysisImageListener(ImageButton imageButton, final View mView){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mView.getContext(), AnalysisActivity.class);
                startActivity(intent);
            }
        });
    }
    public void aboutUsImageListener(ImageButton imageButton, final View mView){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mView.getContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }


    public void changeUserProfile(){

        //When Profile is Loaded
        if (Profile.getUser() != null){
            //Load UserImage
            Picasso.with(getApplicationContext())
                    .load(Profile.getUser().profileImageUrl)
                    .transform(new CircleTransform())
                    .resize(100,100)
                    .into(twitterImageProfile);

            //Set UserName
            txtViewProfileName.setText(Profile.getUser().name);
        }
        loginButton.setVisibility(View.GONE);


        txtViewAboutUs.setVisibility(View.VISIBLE);
        txtViewAnalysis.setVisibility(View.VISIBLE);
        txtViewTwitter.setVisibility(View.VISIBLE);

        imageButtonAboutUs.setVisibility(View.VISIBLE);
        imageButtonAnalysis.setVisibility(View.VISIBLE);
        imageButtonTwitter.setVisibility(View.VISIBLE);

    }

    public void logout(){

        //delet Profile
        Profile.setUser(null);
        Profile.setSession(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
