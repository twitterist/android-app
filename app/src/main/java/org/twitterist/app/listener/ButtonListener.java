package org.twitterist.app.listener;

import android.content.Context;
import android.view.View;

import org.twitterist.app.R;
import org.twitterist.app.activity.AnalysisActivity;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.controller.IntentController;
import org.twitterist.app.controller.TwitterController;

/**
 * Created by marcowuthrich on 23.09.15.
 */
public class ButtonListener implements View.OnClickListener {

   Context context;

    //Default Constructor
    public ButtonListener() {
        context = new Controller().getCurrentView().getContext();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        IntentController intcont = new IntentController();
        switch (id){
            case (R.id.btn_send_analysis_analysis):
                //Send a request
                 new AnalysisActivity().sendTweetToAnalysis();
                break;
            case (R.id.btn_twittern_analysis):
                //Send Tweet and start Twitter Activity
                context.startActivity(intcont.getTwitterIntent(context));
                new TwitterController().sendTweet(new AnalysisActivity().getEditText());
                break;
            case (R.id.btn_history_analysis):
                //Start History Activity
                context.startActivity(intcont.getHistoryIntent(context));
                break;
        }
    }
}
