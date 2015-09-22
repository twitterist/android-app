package org.twitterist.app.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.twitterist.app.Controller;
import org.twitterist.app.R;
import org.twitterist.app.aboutUs.AboutUsActivity;
import org.twitterist.app.analysis.AnalysisActivity;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.login.LoginActivity;
import org.twitterist.app.login.Profile;
import org.twitterist.app.twitter.TwitterActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.fabric.sdk.android.Fabric;


public class HomeActivity extends DrawerMain {

    Controller controller;

    WebView intoTextWeb;
    ImageButton iBTwitter, iBAnalysis, iBAboutUs;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();

        //Twitter AuthConfig Init
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.TWITTER_CONSUMER_KEY), getString(R.string.TWITTER_CONSUMER_SECRET));
        Fabric.with(this, new Twitter(authConfig));




        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.activity_home, null, false);
        mDrawerLayout.addView(mView, 0);

        //set View on Controller
        controller.setCurrentView(mView);

        //UI Elements
        intoTextWeb = (WebView) findViewById(R.id.introText);
        iBTwitter = (ImageButton) findViewById(R.id.imageButton_Twitter_Icon);
        iBAnalysis = (ImageButton) findViewById(R.id.imageButton_Analysis_Icon);
        iBAboutUs = (ImageButton) findViewById(R.id.imageButton_AboutUs_Search_Icon);


        intoTextWeb.getSettings().setJavaScriptEnabled(true);
        intoTextWeb.setBackgroundColor(Color.TRANSPARENT);
        intoTextWeb.loadData(readTextFromResource(R.raw.index_start_page), "text/html", "utf8");


        //Listeners
        iBTwitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Profile.getUser() != null) {
                    intent = new Intent(getApplicationContext(), TwitterActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "You must be Login", Toast.LENGTH_SHORT).show();
                }

            }
        });
        iBAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Profile.getUser() != null) {
                    intent = new Intent(getApplicationContext(), AnalysisActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "You must be Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
        iBAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });


    }

    private String readTextFromResource(int resourceID) {
        InputStream inputStream = getResources().openRawResource(resourceID);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                outStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outStream.toString();
    }

}

