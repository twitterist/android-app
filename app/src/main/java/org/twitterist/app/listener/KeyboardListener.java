package org.twitterist.app.listener;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.twitterist.app.controller.Controller;

/**
 * Created by marcowuthrich on 23.09.15.
 */
public class KeyboardListener implements View.OnFocusChangeListener {

    public KeyboardListener() {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        hideKeyboard(v);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)
                new Controller().getCurrentView().getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
