package org.twitterist.app.drawer;

/**
 * Created by marcowuthrich on 15.09.15.
 */

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.twitterist.app.controller.Controller;
import org.twitterist.app.controller.IntentController;
import org.twitterist.app.model.Profile;


public class DrawerListener implements ListView.OnItemClickListener {

    IntentController intentController;

    private Context context;
    Controller controller;
    DrawerLayout drawerLayout;

    public DrawerListener(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        controller = new Controller();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        //start new Intent
        startNewIntent(position);
        //Drawer close
        if (drawerLayout.isDrawerOpen(Gravity.START | Gravity.LEFT)) {
            drawerLayout.closeDrawers();
        }
    }

    private void startNewIntent(int position) {
        intentController = new IntentController();
        context = controller.getCurrentView().getContext();

        switch (position) {
            case 0:
                //Home
                context.startActivity(intentController.getHomeIntent(context));
                break;
            case 1:
                //Twitter Analysis
                if (Profile.getUser() != null) {
                    context.startActivity(intentController.getAnalysisInten(context));
                } else {
                    mustBeLogin(context);
                }
                break;
            case 2:
                //Twitter
                if (Profile.getUser() != null) {
                    context.startActivity(intentController.getTwitterIntent(context));
                } else {
                    mustBeLogin(context);
                }

                break;
            case 3:
                //History
                context.startActivity(intentController.getHistoryIntent(context));
                break;
            case 4:
                //Login
                context.startActivity(intentController.getAboutUsIntent(context));
                break;
            case 5:
                //Login
                context.startActivity(intentController.getLoginInten(context));
                break;
            default:
                break;
        }

    }


    public void mustBeLogin(Context context) {

        context.startActivity(intentController.getLoginInten(context));

    }
}
