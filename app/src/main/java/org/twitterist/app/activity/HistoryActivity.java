package org.twitterist.app.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.twitterist.app.R;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.drawer.DrawerMain;

public class HistoryActivity extends DrawerMain {

    Controller controller;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        textView = (TextView) findViewById(R.id.history_activity);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.activity_history, null, false);
        mDrawerLayout.addView(mView, 0);


        //set View on Controller
        controller.setCurrentView(mView);
        //  Controller.setActivity(this);

    }



}
