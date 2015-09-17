package org.twitterist.app.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.twitterist.app.Controller;
import org.twitterist.app.R;
import org.twitterist.app.drawer.DrawerMain;
import org.twitterist.app.twitter.TwitterActivity;


public class HomeActivity extends DrawerMain {

    Controller controller;

    TextView textView;
    ImageButton iBTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new Controller();


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.activity_start, null ,false);
        mDrawerLayout.addView(mView, 0);

        //set View on Controller
        controller.setCurrentView(mView);

        //UI Elements
        textView = (TextView) findViewById(R.id.homeActivity);
        iBTwitter =(ImageButton) findViewById(R.id.imageButton_Twitter_Icon);




        //Listeners
        iBTwitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TwitterActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
