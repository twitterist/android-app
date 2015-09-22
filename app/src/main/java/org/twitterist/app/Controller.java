package org.twitterist.app;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by marcowuthrich on 16.09.15.
 */
public class Controller {

    private static View currentView;
    private static DrawerLayout drawerLayout;
    private static View drawerView;

    public Controller() {
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        Controller.currentView = currentView;
    }


    public static DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public static void setDrawerLayout(DrawerLayout drawerLayout) {
        Controller.drawerLayout = drawerLayout;
    }

    public static View getDrawerView() {
        return drawerView;
    }

    public static void setDrawerView(View drawerView) {
        Controller.drawerView = drawerView;
    }
}
