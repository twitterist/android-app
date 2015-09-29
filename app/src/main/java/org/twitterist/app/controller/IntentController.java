package org.twitterist.app.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.twitterist.app.activity.AboutUsActivity;
import org.twitterist.app.activity.AnalysisActivity;
import org.twitterist.app.activity.HistoryActivity;
import org.twitterist.app.activity.HomeActivity;
import org.twitterist.app.activity.LoginActivity;
import org.twitterist.app.model.Profile;

/**
 * Created by marco.wuethrich on 22.09.2015.
 */
public class IntentController {

    private static Intent homeIntent;
    private static Intent analysisInten;
    private static Intent twitterIntent;
    private static Intent historyIntent;
    private static Intent aboutUsIntent;
     public static Intent loginInten;



    //Default Contructor
    public IntentController() {
    }

    public Intent getHomeIntent(Context context) {

        if (homeIntent == null){
            return homeIntent = new Intent(context, HomeActivity.class);
        }else {
            return homeIntent;
        }
    }

    public Intent getAnalysisInten(Context context) {

        if (analysisInten == null){
            return analysisInten = new Intent(context, AnalysisActivity.class);
        }else {
            return analysisInten;
        }
    }

    public Intent getTwitterIntent(Context context) {

        if (twitterIntent == null){
            return ifTwitterPossible(context);
        }else {
            return twitterIntent;
        }
    }

    public Intent getHistoryIntent(Context context) {
        if (historyIntent == null){
            return historyIntent = new Intent(context, HistoryActivity.class);
        }else {
            return historyIntent;
        }
    }

    public Intent getAboutUsIntent(Context context) {
        if (aboutUsIntent == null){
            return aboutUsIntent = new Intent(context, AboutUsActivity.class);
        }else {
            return aboutUsIntent;
        }
    }

    public Intent getLoginInten(Context context) {
        if (loginInten == null){
            return loginInten = new Intent(context, LoginActivity.class);
        }else {
            return loginInten;
        }
    }

    public static Intent ifTwitterPossible(Context context) {

        long userID = Profile.getSession().getUserId();
        try {
            // get the Twitter app if possible
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + userID));
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+userID));
        }
        return twitterIntent;
    }

}

