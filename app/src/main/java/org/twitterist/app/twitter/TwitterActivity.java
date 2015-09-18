package org.twitterist.app.twitter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.twitterist.app.Controller;
import org.twitterist.app.R;
import org.twitterist.app.drawer.DrawerMain;

import java.util.Arrays;
import java.util.List;


public class TwitterActivity extends DrawerMain {

    Controller controller;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new Controller();


       // textView = (TextView) findViewById(R.id.twitter_activity);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.activity_twitter, null, false);
        mDrawerLayout.addView(mView, 0);

        //set View on Controller
        controller.setCurrentView(mView);

        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.tweets);


        final List<Long> tweetIds = Arrays.asList(531132223265992704L, 20L);

        TweetUtils.loadTweets(tweetIds, new LoadCallback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets) {
                for (Tweet tweet : tweets) {
                    Log.v("tweet", tweet.toString());
                    myLayout.addView(new CompactTweetView(TwitterActivity.this, tweet));
                }
            }


            @Override
            public void failure(TwitterException exception) {
                Log.v("hi", exception.getMessage());
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_twitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
