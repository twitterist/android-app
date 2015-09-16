package org.twitterist.app;

import android.view.View;

/**
 * Created by marcowuthrich on 16.09.15.
 */
public class Controller {

    private static View currentView;

    public Controller() {
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        Controller.currentView = currentView;
    }


}
