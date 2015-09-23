package org.twitterist.app.controller;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.twitterist.app.activity.TwitterActivity;

/**
 * Created by marcowuthrich on 23.09.15.
 */
public class TwitterController {

    TweetTimelineListAdapter adapter = null;
    UserTimeline userTimeline;
    Context context;
    ListView tweetList;

    //Default Constructor
    public TwitterController() {
        context = new Controller().getCurrentView().getContext();
    }

    public void sendTweet(String tweetText) {
        TwitterApiClient twitterApiClient = Twitter.getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();

        statusesService.update(tweetText, null, null, null, null, null, null, null, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                new TwitterActivity().initTimeline();
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(context, "Tweet can't send", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }



}
