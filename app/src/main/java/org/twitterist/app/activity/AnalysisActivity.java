package org.twitterist.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.twitterist.app.controller.Controller;
import org.twitterist.app.R;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.activity.HistoryActivity;

public class AnalysisActivity extends DrawerMain {

    Controller controller;
    TextView textViewInfo;
    EditText editTextTweet;
    Button btnAnalysis, btnTwittern, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new Controller();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.activity_analysis, null, false);


        textViewInfo = (TextView) mView.findViewById(R.id.textView_info);
        editTextTweet = (EditText) mView.findViewById(R.id.editText_tweet_analysis);
        btnAnalysis = (Button) mView.findViewById(R.id.button_analysis);
        btnHistory = (Button) mView.findViewById(R.id.button_history_analysis);
        btnTwittern = (Button) mView.findViewById(R.id.button_twittern_analysis);


        mDrawerLayout.addView(mView, 0);


        //set View on Controller
        controller.setCurrentView(mView);
      //  Controller.setActivity(this);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
