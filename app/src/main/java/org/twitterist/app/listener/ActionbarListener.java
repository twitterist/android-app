package org.twitterist.app.listener;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.twitterist.app.controller.Controller;

/**
 * Created by marcowuthrich on 23.09.15.
 */
public class ActionbarListener implements View.OnTouchListener{

    //Default Constructor
    public ActionbarListener() {
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) new Controller().getCurrentView().getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        return false;
    }
}
