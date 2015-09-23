package org.twitterist.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.twitterist.app.R;
import org.twitterist.app.controller.AnalysisController;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.listener.ButtonListener;
import org.twitterist.app.listener.KeyboardListener;

public class AnalysisActivity extends DrawerMain {

    Controller controller;
    TextView textViewInfo;
    static EditText editTextTweet;
    Button btnAnalysis, btnTwittern, btnHistory;

    public final int MAX_TWEEET_LENGHT = 240;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new Controller();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.activity_analysis, null, false);


        textViewInfo = (TextView) mView.findViewById(R.id.textView_info);
        editTextTweet = (EditText) mView.findViewById(R.id.editText_tweet_analysis);
        btnAnalysis = (Button) mView.findViewById(R.id.btn_send_analysis_analysis);
        btnHistory = (Button) mView.findViewById(R.id.btn_history_analysis);
        btnTwittern = (Button) mView.findViewById(R.id.btn_twittern_analysis);


        mDrawerLayout.addView(mView, 0);


        //set View on Controller
        controller.setCurrentView(mView);

        //Listener
        editTextTweet.setOnFocusChangeListener(new KeyboardListener());
        btnTwittern.setOnClickListener(new ButtonListener());
        btnHistory.setOnClickListener(new ButtonListener());
        btnAnalysis.setOnClickListener(new ButtonListener());
    }

    public boolean sendTweetToAnalysis() {
        String tweetText = editTextTweet.getText().toString();

        if (tweetText.length() > 0 && tweetText.length() < MAX_TWEEET_LENGHT) {
            new AnalysisController().sendRequest(tweetText);
            return true;
        } else {
            return false;
        }

    }

    public String getEditText() {
        return editTextTweet.getText().toString();
    }

}
