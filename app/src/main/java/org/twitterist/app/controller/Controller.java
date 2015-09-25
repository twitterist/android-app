package org.twitterist.app.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import org.twitterist.app.model.Profile;

import java.util.Timer;
import java.util.TimerTask;

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

    public void logoutButtonVisible() {

        //if a Session hear Logout btn is visible else no fisible
        if (Profile.getSession() != null && menuLogout != null) {
            menuLogout.setVisible(true);
        } else if (Profile.getSession() == null && menuLogout != null) {
            menuLogout.setVisible(false);
        }
    }

    public void showAlertDialogPleaseLogin(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Please Login");
        builder.setMessage("After 2 second, this dialog will be closed automatically!");
        builder.setCancelable(true);

        final AlertDialog dlg = builder.create();

        dlg.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {

                dlg.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report

            }
        }, 2000); // after 2 second (or 2000 miliseconds), the task will be active.

    }

}
