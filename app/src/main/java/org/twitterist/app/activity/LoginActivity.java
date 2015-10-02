package org.twitterist.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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

import org.twitterist.app.R;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.controller.IntentController;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.listener.ImageButtonListener;
import org.twitterist.app.model.CircleTransform;
import org.twitterist.app.model.Profile;

public class LoginActivity extends DrawerMain {

    Context context;
    TwitterLoginButton loginButton;
    ImageButton imageButtonTwitter, imageButtonAboutUs, imageButtonAnalysis;
    ImageView twitterImageProfile;
    TextView txtViewProfileName, txtViewTwitter, txtViewAnalysis, txtViewAboutUs;
    FrameLayout loginframe;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mView = inflater.inflate(R.layout.activity_login, null, false);
        new Controller().setCurrentView((mView));
        context = new Controller().getCurrentView().getContext();
        mDrawerLayout.addView(mView, 0);

        //UI
        twitterImageProfile = (ImageView) mView.findViewById(R.id.imageView_user_profile);
        txtViewProfileName = (TextView) mView.findViewById(R.id.txtViewProfileName);
        loginButton = (TwitterLoginButton) mView.findViewById(R.id.twitter_login_button);
        imageButtonTwitter = (ImageButton) mView.findViewById(R.id.imageButton_Twitter_Login);
        imageButtonAboutUs = (ImageButton) mView.findViewById(R.id.imageButton_AboutUS_Login);
        imageButtonAnalysis = (ImageButton) mView.findViewById(R.id.imageButton_Analysis_Login);
        txtViewTwitter = (TextView) mView.findViewById(R.id.textViewTwitter);
        txtViewAnalysis = (TextView) mView.findViewById(R.id.textViewAnalysis);
        txtViewAboutUs = (TextView) mView.findViewById(R.id.textViewAboutUs);
        loginframe = (FrameLayout) mView.findViewById(R.id.frameLayout_Twitter_Login_btn);


        //Init
        if (Profile.getUser() == null) {
            initNotLogin();
        } else {
            //Change to Profile
            initUserProfile();
        }
        //Listener
        imageButtonAboutUs.setOnClickListener(new ImageButtonListener());
        imageButtonAnalysis.setOnClickListener(new ImageButtonListener());
        imageButtonTwitter.setOnClickListener(new ImageButtonListener());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void initNotLogin() {
        //UI
        txtViewAboutUs.setVisibility(View.GONE);
        txtViewAnalysis.setVisibility(View.GONE);
        txtViewTwitter.setVisibility(View.GONE);

        imageButtonAboutUs.setVisibility(View.GONE);
        imageButtonAnalysis.setVisibility(View.GONE);
        imageButtonTwitter.setVisibility(View.GONE);

        loginframe.setVisibility(View.VISIBLE);

        //Login Listener
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
                                startActivity(new IntentController().getLoginInten(context));
                            }
                        });
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Login", exception.getMessage());
            }
        });
    }

    public void initUserProfile() {
        //Show Logout Btn on ActionBar
        new Controller().logoutButtonVisible();
        new DrawerMain();

        //When Profile is Loaded
        if (Profile.getUser() != null) {
            //Load UserImage
            Picasso.with(getApplicationContext())
                    .load(Profile.getUser().profileImageUrl)
                    .transform(new CircleTransform())
                    .resize(100, 100)
                    .into(twitterImageProfile);

            //Set UserName
            txtViewProfileName.setText(Profile.getUser().name);
        }
        //UI
        loginButton.setVisibility(View.GONE);

        txtViewAboutUs.setVisibility(View.VISIBLE);
        txtViewAnalysis.setVisibility(View.VISIBLE);
        txtViewTwitter.setVisibility(View.VISIBLE);

        imageButtonAboutUs.setVisibility(View.VISIBLE);
        imageButtonAnalysis.setVisibility(View.VISIBLE);
        imageButtonTwitter.setVisibility(View.VISIBLE);

        loginframe.setVisibility(View.GONE);
    }

    public void logout() {
        //delete Profile preferences
        Profile.setUser(null);
        Profile.setSession(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showAlertDialogForLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new Controller().getCurrentView().getContext());

        builder.setTitle("Logout");
        builder.setMessage("Do you really want to log out?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //Hidden Logout Btn on ActionBar, Logout
                new LoginActivity().logout();
                new Controller().logoutButtonVisible();
                Context context = new Controller().getCurrentView().getContext();
                context.startActivity(new IntentController().getHomeIntent(context));
                dialog.dismiss();
            }

        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
