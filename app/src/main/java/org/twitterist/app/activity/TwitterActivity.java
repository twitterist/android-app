package org.twitterist.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.twitterist.app.controller.Controller;
import org.twitterist.app.R;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.model.Profile;


public class TwitterActivity extends DrawerMain {

    Controller controller;
    ListView tweetList;
    EditText editTextTweetString;
    Button btnTweetSend;
    String tweetText;
    TweetTimelineListAdapter adapter = null;
    UserTimeline userTimeline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Drawer
        controller = new Controller();
        // textView = (TextView) findViewById(R.id.twitter_activity);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mView = inflater.inflate(R.layout.activity_twitter, null, false);
        mDrawerLayout.addView(mView, 0);

        tweetList = (ListView) mView.findViewById(R.id.list);
        editTextTweetString = (EditText) mView.findViewById(R.id.editText_tweet_String);
        btnTweetSend = (Button) mView.findViewById(R.id.btn_send_tweet);

        initTimeline();


        //set View on Controller
        controller.setCurrentView(mView);

        btnTweetSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweetText = editTextTweetString.getText().toString();

                //If Text right length
                if (tweetText.length() > 0 && tweetText.length() < 120) {
                    sendTweet(tweetText);


                } else {
                    Toast.makeText(getApplicationContext(), "Bitte Ueberpruefe deine Eingaben", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void initTimeline() {


        userTimeline = new UserTimeline.Builder()
                .userId(Profile.getUser().id)
                .build();
        adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        tweetList.setAdapter(adapter);

    }

    public void sendTweet(String tweetText) {


        TwitterApiClient twitterApiClient = Twitter.getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();

        statusesService.update(tweetText, null, null, null, null, null, null, null, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                initTimeline();
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(getApplicationContext(),"Tweet can't send",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

}
