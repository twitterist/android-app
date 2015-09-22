package org.twitterist.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.twitterist.app.controller.Controller;
import org.twitterist.app.R;
import org.twitterist.app.drawer.DrawerMain;

public class AboutUsActivity extends DrawerMain {

    Controller controller;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();


        textView = (TextView) findViewById(R.id.aboutUs_activity);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.activity_about_us, null ,false);
        mDrawerLayout.addView(mView, 0);



        //set View on Controller
        controller.setCurrentView(mView);
       // Controller.setActivity(this);

    }

}
