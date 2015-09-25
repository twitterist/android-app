package org.twitterist.app.listener;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.twitterist.app.R;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.controller.IntentController;
import org.twitterist.app.model.Profile;

/**
 * Created by marcowuthrich on 22.09.15.
 */
public class ImageButtonListener implements ImageView.OnClickListener {

    Context context;
    IntentController intentController;

    public ImageButtonListener() {
        context = new Controller().getCurrentView().getContext();
        intentController = new IntentController();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //Twitter
            case (R.id.imageButton_Twitter_Home):
            case (R.id.imageButton_Twitter_Login):

                if (Profile.getUser() != null) {
                    context.startActivity(intentController.getTwitterIntent(context));
                } else {
                    context.startActivity(intentController.getLoginInten(context));

                }
                break;


            //Analysis
            case (R.id.imageButton_Analysis_Home):
            case (R.id.imageButton_Analysis_Login):

                if (Profile.getUser() != null) {

                    context.startActivity(intentController.getAnalysisInten(context));
                } else {
                    context.startActivity(intentController.getLoginInten(context));

                }
                break;

            case (R.id.imageButton_AboutUs_Home):
            case (R.id.imageButton_AboutUS_Login):
                context.startActivity(intentController.getAboutUsIntent(context));
                break;

            default:
                break;
        }


    }
}
