package org.twitterist.app.drawer;

/**
 * Created by marcowuthrich on 15.09.15.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.twitterist.app.Controller;
import org.twitterist.app.IntentController;
import org.twitterist.app.login.Profile;

import java.lang.ref.WeakReference;


public class DrawerListener implements ListView.OnItemClickListener {

    Controller controller;
    IntentController intentController;

    WeakReference<DrawerLayout> mDrawerLayout;
    FragmentManager fm;
    private Context ctx;

    public DrawerListener(Context ctx, WeakReference<DrawerLayout> mDrawerLayout, FragmentManager fm) {
        this.mDrawerLayout = mDrawerLayout;
        this.fm = fm;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        displayView(position);

        if (Controller.getDrawerLayout().isDrawerOpen(Gravity.START | Gravity.LEFT)){
            Controller.getDrawerLayout().closeDrawers();
        }


    }

    private void displayView(int position) {
        controller = new Controller();
        intentController = new IntentController();
        this.ctx = controller.getCurrentView().getContext();
        Intent intent = null;
        switch (position) {
            case 0:
                //Home
                ctx.startActivity(intentController.getHomeIntent());
                break;
            case 1:
                //Twitter Analysis
                if (Profile.getUser() != null) {
                    ctx.startActivity(intentController.getAnalysisInten());
                } else {
                    mustBeLogin(ctx);
                }


                break;
            case 2:
                //Twitter
                if (Profile.getUser() != null) {
                    ctx.startActivity(intentController.getTwitterIntent());
                } else {
                    mustBeLogin(ctx);
                }

                break;
            case 3:
                //History
                ctx.startActivity(intentController.getHistoryIntent());
            break;
            case 4:
                //Login
                ctx.startActivity(intentController.getAboutUsIntent());
                break;
            case 5:
                //Login
                ctx.startActivity(intentController.getLoginInten());
                break;
            default:
                break;
        }

    }


    public void mustBeLogin( Context context) {
        ctx.startActivity(intentController.getLoginInten());
        Toast.makeText(context, "You must be Login", Toast.LENGTH_SHORT).show();
    }
}
