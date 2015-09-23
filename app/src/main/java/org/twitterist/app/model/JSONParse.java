package org.twitterist.app.model;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marco.wuethrich on 21.09.2015.
 */
public class JSONParse {

    private JSONResponseObject jObjekt;
    JSONObject json = null;
    HttpResponse response;
    final String TAG_ROOT = "Entry";
    final String TAG_STATUS = "TAG_STATUS";
    final String TAG_RESULT = "result";
    final String TAG_TWEET = "tweet";
    final String TAG_USERID = "userID";

    final String LOG_TAG = "JSON";


    //Default Constructor
    public JSONParse() {
    }


    public JSONResponseObject getJSONObject(String jsonText) {

        //Parse JSON from String to JObjects
        if (jsonText != null) {
            try {
                //Entry save Root TAG in a Array
                json = new JSONObject(jsonText);
                JSONArray jsonArray = json.optJSONArray(TAG_ROOT);
                //Create own Object and fill it
                jObjekt = new JSONResponseObject();
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Save the right Array in a Object
                    JSONObject childNode = jsonArray.getJSONObject(i);
                    // Save Value from Tag in Object
                    jObjekt.setStatus(childNode.optString(TAG_STATUS));
                    jObjekt.setResult(Double.parseDouble(childNode.optString(TAG_RESULT)));
                    //many more
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "ERROR in JSON Parser from string");
            }
            return jObjekt;
        } else {
            Log.i(LOG_TAG, "String from Response is Empty");
        }
        return null;
    }

    //Create a JSONObject with a Tweet und UserID
    public JSONObject writeJSONObject(String tweet, long id) {
        JSONObject obj, jsonObject = null;
        JSONArray jsonArray = new JSONArray();

        //Write JSON
        obj = new JSONObject();
        try {
            obj.put(TAG_USERID, tweet);
            obj.put(TAG_TWEET, id);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "ERROR by Write JSON Obj");
        }
        jsonArray.put(obj);

        //Put Array to Finally JSON Object with Root Tag
        try {
            jsonObject = new JSONObject();
            jsonObject.put(TAG_ROOT, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "ERROR by write array in finally JSONObject");
        }
        return jsonObject;
    }
}


