package org.twitterist.app.drawer;

/**
 * Created by marcowuthrich on 15.09.15.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.twitterist.app.Controller;
import org.twitterist.app.aboutUs.AboutUsActivity;
import org.twitterist.app.analysis.AnalysisActivity;
import org.twitterist.app.history.HistoryActivity;
import org.twitterist.app.home.HomeActivity;
import org.twitterist.app.login.LoginActivity;
import org.twitterist.app.twitter.TwitterActivity;

import java.lang.ref.WeakReference;


public class DrawerListener implements  ListView.OnItemClickListener{

    Controller controller;

    WeakReference<DrawerLayout> mDrawerLayout;
    FragmentManager fm;
    private Context ctx;

    public DrawerListener(Context ctx, WeakReference<DrawerLayout> mDrawerLayout, FragmentManager fm)
    {
        this.mDrawerLayout = mDrawerLayout;
        this.fm = fm;
        this.ctx = ctx;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        displayView(position);
    }

    private void displayView(int position) {

        controller = new Controller();
        View currentView = controller.getCurrentView();
        Intent intent;
        switch (position) {
            case 0:
                //Home
                intent = new Intent(currentView.getContext(), HomeActivity.class);
                currentView.getContext().startActivity(intent);
                break;
            case 1:
                //Twitter Analysis
                intent = new Intent(currentView.getContext(), AnalysisActivity.class);
                currentView.getContext().startActivity(intent);
                break;
            case 2:
                //Twitter
                intent = new Intent(currentView.getContext(), TwitterActivity.class);
                currentView.getContext().startActivity(intent);
                break;
            case 3:
                //History
                intent = new Intent(currentView.getContext(), HistoryActivity.class);
                currentView.getContext().startActivity(intent);
                break;
            case 4:
                //Login
                intent = new Intent(currentView.getContext(), AboutUsActivity.class);
                currentView.getContext().startActivity(intent);
                break;
            case 5:
                //Login
                intent = new Intent(currentView.getContext(), LoginActivity.class);
                currentView.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }
}
