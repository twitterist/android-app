package org.twitterist.app.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.twitterist.app.R;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.controller.IntentController;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.listener.ImageButtonListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.fabric.sdk.android.Fabric;


public class HomeActivity extends DrawerMain {

    Controller controller;

    WebView intoTextWeb;
    ImageButton iBTwitter, iBAnalysis, iBAboutUs;
    IntentController intentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        intentController = new IntentController();

        //Twitter AuthConfig Init
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.TWITTER_CONSUMER_KEY), getString(R.string.TWITTER_CONSUMER_SECRET));
        Fabric.with(this, new Twitter(authConfig));

        //init View, Drawer
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.activity_home, null, false);
        mDrawerLayout.addView(mView, 0);

        //set View on Controller
        controller.setCurrentView(mView);


        //UI Elements
        intoTextWeb = (WebView) findViewById(R.id.introText);
        iBTwitter = (ImageButton) findViewById(R.id.imageButton_Twitter_Home);
        iBAnalysis = (ImageButton) findViewById(R.id.imageButton_Analysis_Home);
        iBAboutUs = (ImageButton) findViewById(R.id.imageButton_AboutUs_Home);
        //WebView
        intoTextWeb.getSettings().setJavaScriptEnabled(true);
        intoTextWeb.setBackgroundColor(Color.TRANSPARENT);
        intoTextWeb.loadData(readTextFromResource(R.raw.index_start_page), "text/html", "utf8");


        //Listeners
        iBTwitter.setOnClickListener(new ImageButtonListener());
        iBAnalysis.setOnClickListener(new ImageButtonListener());
        iBAboutUs.setOnClickListener(new ImageButtonListener());


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

