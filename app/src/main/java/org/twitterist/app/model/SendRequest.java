package org.twitterist.app.model;



import android.content.Context;
import android.util.Log;

import com.mopub.volley.Request;
import com.mopub.volley.RequestQueue;
import com.mopub.volley.Response;
import com.mopub.volley.VolleyError;
import com.mopub.volley.toolbox.StringRequest;
import com.mopub.volley.toolbox.Volley;

import org.json.JSONObject;
import org.twitterist.app.controller.Controller;
import org.twitterist.app.controller.RequestResopnseController;

/**
 * Created by marco.wuethrich on 23.09.2015.
 */
public class SendRequest {

    Context context;
    final String LOG_TAG = "Request";

    String url = "http://www.twitterist.org/request";

    RequestResopnseController outController;

    //Default Constructor
    public SendRequest() {
        context = new Controller().getCurrentView().getContext();
        outController = new RequestResopnseController();
    }

    //Send Request with Post
    public void sendRequest(JSONObject jsonObject){
        //Init RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Give the Request String continue
                outController.setResponseString(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG,"Error: "+error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }

}
