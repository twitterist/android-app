package org.twitterist.app.controller;

import org.json.JSONObject;
import org.twitterist.app.model.DownloadTask;
import org.twitterist.app.model.JSONParse;
import org.twitterist.app.model.JSONResponseObject;
import org.twitterist.app.model.Profile;

/**
 * Created by marco.wuethrich on 23.09.2015.
 */
public class AnalysisController {

    private String responseString;
    private String tweetText;
    public final String REQUESTURL = "https://www.twitterist.org";
    private static JSONObject jsonObjectRequest;
    private JSONResponseObject jsonObjectResponse;

    //Default Constructor
    public AnalysisController() {
    }

    public JSONObject getJsonObjectRequest() {
        return jsonObjectRequest;
    }

    public void sendRequest(String tweetText) {
        this.tweetText = tweetText;
        long id = Profile.getUser().getId();

        //Create a JSON Object with the Tweet und UserID
        jsonObjectRequest = new JSONParse().writeJSONObject(tweetText, id);

        //Send Request
        if (new DownloadTask().isConnectionToInternet()) {
            new DownloadTask().execute(REQUESTURL);
        }
    }


    public void analysisFinish(String responseString){
        this.responseString = responseString;

        jsonObjectResponse = new JSONParse().getJSONObject(responseString);
        //TODO Make Something mit de Result: JsonObjects
    }
}
