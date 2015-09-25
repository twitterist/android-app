package org.twitterist.app.drawer;

/**
 * Created by marcowuthrich on 15.09.15.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.twitterist.app.R;
import org.twitterist.app.activity.LoginActivity;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.controller.IntentController;
import org.twitterist.app.model.Profile;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;


public class DrawerMain extends ActionBarActivity {

    public android.support.v7.widget.Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    public ListView mDrawerList;
    MenuItem menuLogut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Twitter init
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.TWITTER_CONSUMER_KEY), getString(R.string.TWITTER_CONSUMER_SECRET));
        Fabric.with(this, new Twitter(authConfig));


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mView = inflater.inflate(R.layout.activity_drawer, null, false);


        setContentView(R.layout.activity_drawer);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setSupportActionBar(mToolbar);
        //For API 11 or Higher
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        String[] nameoptionList = getResources().getStringArray(R.array.nav_drawer_items);



        // If you want to apply different icon for items, use the other constructor for DrawerItem.
        ArrayList<DrawerItem> navDrawerItems = new ArrayList<DrawerItem>();
        for (String aNameoptionList : nameoptionList) {
            navDrawerItems.add(new DrawerItem(aNameoptionList));
        }

        DrawerListAdapter adapter = new DrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        //Listener
        mDrawerList.setOnItemClickListener(new DrawerListener(mDrawerLayout));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Logout btn an Action Bar
            case (R.id.textView_Logout_actionBar):
                showAlertDialogForLogout();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        this.menuLogut = menu.findItem(R.id.textView_Logout_actionBar);
        new Controller().setMenuItemLogout(menuLogut);
        //Show Logout Btn on ActionBar
        new Controller().logoutButtonVisible();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    public void showAlertDialogForLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

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
