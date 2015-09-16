package org.twitterist.app.drawer;

/**
 * Created by marcowuthrich on 15.09.15.
 */

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.twitterist.app.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;


public class DrawerMain extends ActionBarActivity {

    public android.support.v7.widget.Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    public ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Twitter init
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.TWITTER_CONSUMER_KEY),getString(R.string.TWITTER_CONSUMER_SECRET));
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_main);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setSupportActionBar(mToolbar);
        mDrawerToggle= new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        String[] nameoptionList = getResources().getStringArray(R.array.nav_drawer_items);

        // If you want to apply different icon for items, use the other constructor for DrawerItem.
        ArrayList<DrawerItem> navDrawerItems = new ArrayList<DrawerItem>();
        for (int i = 0 ; i < nameoptionList.length ; i++){
            navDrawerItems.add(new DrawerItem(nameoptionList[i]));
        }

        DrawerListAdapter adapter = new DrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerListener(getApplicationContext(),
                new WeakReference<DrawerLayout>(mDrawerLayout),
                getSupportFragmentManager()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}