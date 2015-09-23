package org.twitterist.app.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONObject;
import org.twitterist.app.controller.AnalysisController;
import org.twitterist.app.controller.Controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by marco.wuethrich on 23.09.2015.
 */
public class DownloadTask extends AsyncTask<String, Void, String> {

    Context context;
    final String LOG_TAG = "Download";

    //Default Constructor
    public DownloadTask() {
        context = new Controller().getCurrentView().getContext();
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            Log.e(LOG_TAG, "May be url false: " + e.getMessage());
            e.getStackTrace();
            return e.getMessage();
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        //Retourn
    }

    private String downloadUrl(String myurl) throws IOException {

        JSONObject jsonObject = new AnalysisController().getJsonObjectRequest();
        String responseString = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(myurl);

        // Set parameters
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000); //Miliseconds
        HttpConnectionParams.setSoTimeout(params, 5000); //Miliseconds

        // Add your data
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("json", jsonObject.toString()));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);

        // Add Streams
        InputStream inputStream = response.getEntity().getContent();
        BufferedInputStream buffInSteam = new BufferedInputStream(inputStream);
        ByteArrayBuffer byteArrBuff = new ByteArrayBuffer(50);

        try {
            int current = 0;
            // while stream can read
            while ((current = buffInSteam.read()) != -1) {
                byteArrBuff.append((byte) current);
            }
            // Convert the Bytes to String
            responseString = new String(byteArrBuff.toByteArray());
        } catch (IOException e) {
            e.getStackTrace();


        } finally {
            inputStream.close();
            buffInSteam.close();
        }
        return responseString;
    }

    //Consider Networkconnectoin
    public boolean isConnectionToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;

    }
}
