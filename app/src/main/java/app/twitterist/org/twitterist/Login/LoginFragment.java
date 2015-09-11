package app.twitterist.org.twitterist.login;

import android.app.Application;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import app.twitterist.org.twitterist.R;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class LoginFragment extends Fragment {

    private ProgressDialog pDialog;


    private static RequestToken requestToken;

    //UI Elements
    private ImageButton imageBtnLogin, imageBtnLogout;

    protected View mView;
   // private static TwitterStream twitterStream;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //Construcor with 2 Arguments
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Default Constructor
    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Init
        this.mView = inflater.inflate(R.layout.fragment_login_container, container, false);

		/* Enabling strict mode */
        // IF SDK > 8
        if (android.os.Build.VERSION.SDK_INT > 8) {
            ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        // UI Elements
        imageBtnLogin = (ImageButton) mView.findViewById(R.id.imageBtnLogin);
        imageBtnLogout = (ImageButton) mView.findViewById(R.id.imageBtnLogout);


        /* Check if required twitter keys are set */
        if (TextUtils.isEmpty(getString(R.string.TWITTER_CONSUMER_KEY)) || TextUtils.isEmpty(getString(R.string.TWITTER_CONSUMER_SECRET))) {
            Toast.makeText(getActivity(), "Twitter key and secret not configured",
                    Toast.LENGTH_SHORT).show();
        }

        //init Shared Preference
        UserData.setmSharedPreferences(this.getActivity().getSharedPreferences(getString(R.string.PREFERENCE_NAME), Context.MODE_PRIVATE));

        Uri uri = getActivity().getIntent().getData();
         		if (uri != null && uri.toString().startsWith(getString(R.string.TWITTER_CALLBACK_URL))) {
             			String verifier = uri.getQueryParameter(getString(R.string.URL_TWITTER_OAUTH_VERIFIER));
                       try {
                                 AccessToken accessToken = UserData.getTwitter().getOAuthAccessToken(requestToken, verifier);
                                 SharedPreferences.Editor e = UserData.getmSharedPreferences().edit();
                                 e.putString(getString(R.string.PREF_KEY_OAUTH_TOKEN), accessToken.getToken());
                                 e.putString(getString(R.string.PREF_KEY_OAUTH_SECRET), accessToken.getTokenSecret());
                                 e.commit();
                 	        } catch (Exception e) {
                 	                Log.e(getTag(), e.getMessage());
                 	                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                 			}
                     }


        //login Button Listener
        imageBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToTwitter();
            }
        });
        //logout Button Listener
        imageBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectTwitter();

            }
        });

        // Inflate the layout for this fragment
        return mView;
    }

    private void disconnectTwitter() {

        SharedPreferences.Editor editor = UserData.getmSharedPreferences().edit();

        editor.remove(getString(R.string.PREF_KEY_OAUTH_TOKEN));
        editor.remove(getString(R.string.PREF_KEY_OAUTH_SECRET));
        editor.commit();
    }


    private void saveTwitterInfo(AccessToken accessToken) {
        long userID = accessToken.getUserId();
        User user;
        try {
            user = UserData.getTwitter().showUser(userID);
            String username = user.getName();
            /* Storing oAuth tokens to shared preferences */
            SharedPreferences.Editor e = UserData.getmSharedPreferences().edit();
            e.putString(getString(R.string.PREF_KEY_OAUTH_TOKEN), accessToken.getToken());
            e.putString(getString(R.string.PREF_KEY_OAUTH_SECRET), accessToken.getTokenSecret());
            e.putBoolean(getString(R.string.PREF_KEY_TWITTER_LOGIN), true);
            e.putString(getString(R.string.PREF_USER_NAME), username);
            e.apply();
            Log.d("login", e.toString());
        } catch (TwitterException e1) {
            e1.printStackTrace();
        }
    }


    private void loginToTwitter() {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(getString(R.string.TWITTER_CONSUMER_KEY));
        configurationBuilder.setOAuthConsumerSecret(getString(R.string.TWITTER_CONSUMER_SECRET));
        Configuration configuration = configurationBuilder.build();
        UserData.setTwitter(new TwitterFactory(configuration).getInstance());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        try {
            requestToken = UserData.getTwitter().getOAuthRequestToken(getString(R.string.TWITTER_CALLBACK_URL));
            Toast.makeText(getActivity(), "Please authorize this app!", Toast.LENGTH_LONG).show();

            
            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));

        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }


    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return UserData.getmSharedPreferences().getBoolean(getString(R.string.PREF_KEY_TWITTER_LOGIN), false);
    }

    private void saveAccessToken(AccessToken at) {
        String token = at.getToken();
        String secret = at.getTokenSecret();
        SharedPreferences.Editor editor = UserData.getmSharedPreferences().edit();
        editor.putString(getString(R.string.PREF_KEY_OAUTH_TOKEN), token);
        editor.putString(getString(R.string.PREF_KEY_OAUTH_SECRET), secret);
        editor.commit();
    }

    /**
     * check if the account is authorized
     *
     * @return
     */
    private boolean isConnected() {
        return UserData.getmSharedPreferences().getString(getString(R.string.PREF_KEY_OAUTH_TOKEN), null) != null;
    }
/*

    @Override
    public void onResume() {
        super.onResume();

        if (isConnected()) {
            String oauthAccessToken = getString(R.string.PREF_KEY_OAUTH_TOKEN);
            String oAuthAccessTokenSecret = getString(R.string.PREF_KEY_OAUTH_SECRET);


            ConfigurationBuilder confbuilder = new ConfigurationBuilder();
            Configuration conf = confbuilder
                    .setOAuthConsumerKey(getString(R.string.TWITTER_CONSUMER_KEY))
                    .setOAuthConsumerSecret(getString(R.string.TWITTER_CONSUMER_SECRET))
                    .setOAuthAccessToken(getString(R.string.PREF_KEY_OAUTH_TOKEN))
                    .setOAuthAccessTokenSecret(getString(R.string.PREF_KEY_OAUTH_SECRET))
                    .build();


            twitterStream = new TwitterFactory(conf).getInstance();

            buttonLogin.setText(R.string.label_disconnect);
            getTweetButton.setEnabled(true);
        } else {
            buttonLogin.setText(R.string.label_connect);
        }
    }
*/


}
