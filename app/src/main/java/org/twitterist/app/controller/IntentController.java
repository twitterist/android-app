package org.twitterist.app.controller;

import android.content.Context;
import android.content.Intent;

import org.twitterist.app.activity.AboutUsActivity;
import org.twitterist.app.activity.AnalysisActivity;
import org.twitterist.app.activity.HistoryActivity;
import org.twitterist.app.activity.HomeActivity;
import org.twitterist.app.activity.LoginActivity;
import org.twitterist.app.activity.TwitterActivity;

/**
 * Created by marco.wuethrich on 22.09.2015.
 */
public class IntentController {

    private static Intent homeIntent;
    private static Intent analysisInten;
    private static Intent twitterIntent;
    private static Intent historyIntent;
    private static Intent aboutUsIntent;
    private static Intent loginInten;



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
            return twitterIntent = new Intent(context, TwitterActivity.class);
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

}


