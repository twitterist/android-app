package org.twitterist.app;

import android.content.Context;
import android.content.Intent;

import org.twitterist.app.aboutUs.AboutUsActivity;
import org.twitterist.app.analysis.AnalysisActivity;
import org.twitterist.app.history.HistoryActivity;
import org.twitterist.app.home.HomeActivity;
import org.twitterist.app.login.LoginActivity;
import org.twitterist.app.twitter.TwitterActivity;

/**
 * Created by marco.wuethrich on 22.09.2015.
 */
public class IntentController {

    private Intent homeIntent;
    private Intent analysisInten;
    private Intent twitterIntent;
    private Intent historyIntent;
    private Intent aboutUsIntent;
    private Intent loginInten;
    private Controller controller;
    private Context currentContext;


    public IntentController() {

    }


    public Intent getHomeIntent() {
        return homeIntent;
    }

    public Intent getAnalysisInten() {
        return analysisInten;
    }

    public Intent getTwitterIntent() {
        return twitterIntent;
    }

    public Intent getHistoryIntent() {
        return historyIntent;
    }

    public Intent getAboutUsIntent() {
        return aboutUsIntent;
    }

    public Intent getLoginInten() {
        return loginInten;
    }

    public void initIntent(){
        controller = new Controller();
        currentContext = controller.getCurrentView().getContext();
        this.homeIntent = new Intent(currentContext, HomeActivity.class);
        this.analysisInten = new Intent(currentContext, AnalysisActivity.class);
        this.twitterIntent = new Intent(currentContext, TwitterActivity.class);
        this.historyIntent = new Intent(currentContext, HistoryActivity.class);
        this.aboutUsIntent = new Intent(currentContext, AboutUsActivity.class);
        this.loginInten = new Intent(currentContext, LoginActivity.class);
    }
}


