package org.twitterist.app.analysis;

import android.os.StrictMode;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by marco.wuethrich on 21.09.2015.
 */
public class JSONParse {

    private JObject jObjekt;

    public JSONParse(String url) {
        getJSONObject(url);
    }

    public JObject getJSONObject(String url) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDialog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
                .penaltyLog()
                .build());


        JSONObject json = null;
        String str = "";
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost(url);

        try {
            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            JSONArray jArray = new JSONArray(str);
            json = jArray.getJSONObject(0);

            jObjekt = new JObject();

            jObjekt.setStatus(json.getString("status"));
            jObjekt.setResult(json.getDouble("result"));
            ///...

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObjekt;
    }
}


