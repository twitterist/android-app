package org.twitterist.app.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.twitterist.app.Controller;
import org.twitterist.app.R;
import org.twitterist.app.drawer.DrawerMain;

public class AnalysisActivity extends DrawerMain {

    Controller controller;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new Controller();


        textView = (TextView) findViewById(R.id.analysis_activity);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.activity_analysis, null ,false);
        mDrawerLayout.addView(mView, 0);



        //set View on Controller
        controller.setCurrentView(mView);
    }


}
