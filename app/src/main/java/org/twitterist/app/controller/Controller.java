package org.twitterist.app.controller;

import android.view.MenuItem;
import android.view.View;

import org.twitterist.app.model.Profile;

/**
 * Created by marcowuthrich on 16.09.15.
 */
public class Controller {

    private static View currentView;
    private static MenuItem menuLogout;

    //Defalt Constructor
    public Controller() {
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        Controller.currentView = currentView;
    }

    public MenuItem getMenuItemLogout() {
        return menuLogout;
    }

    public void setMenuItemLogout(MenuItem menuItemLogout) {
        Controller.menuLogout = menuItemLogout;
    }

    public void logoutButtonVisible(){

        //if a Session hear Logout btn is visible else no fisible
        if (Profile.getSession() != null && menuLogout != null){
            menuLogout.setVisible(true);
        }else if (Profile.getSession() == null && menuLogout != null) {
            menuLogout.setVisible(false);
        }
    }
}
