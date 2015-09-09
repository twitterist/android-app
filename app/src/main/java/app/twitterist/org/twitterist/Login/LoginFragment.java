package app.twitterist.org.twitterist.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    /* Shared preference keys */
    private static final String PREF_NAME = "sample_twitter_pref";
    private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
    private static final String PREF_USER_NAME = "twitter_user_name";

    /* Any number for uniquely distinguish your request */
    public static final int WEBVIEW_REQUEST_CODE = 100;

    private ProgressDialog pDialog;

    private static Twitter twitter;
    private static RequestToken requestToken;

    private static SharedPreferences mSharedPreferences;

    private TextView userName;
    private ImageButton imageBtnLogin;

    private String consumerKey = null;
    private String consumerSecret = null;
    private String callbackUrl = null;
    private String oAuthVerifier = null;

    protected View mView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
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

        /* initializing twitter parameters from string.xml */
        initTwitterConfigs();

		/* Enabling strict mode */
        // IF SDK > 8
        if (android.os.Build.VERSION.SDK_INT > 8) {
            ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        this.mView = inflater.inflate(R.layout.fragment_login_container, container, false);

        // UI Elements
        imageBtnLogin = (ImageButton) mView.findViewById(R.id.imageBtnLogin);
        userName = (TextView) mView.findViewById(R.id.username);

        /* Check if required twitter keys are set */
        if (TextUtils.isEmpty(consumerKey) || TextUtils.isEmpty(consumerSecret)) {
            Toast.makeText(getActivity(), "Twitter key and secret not configured",
                    Toast.LENGTH_SHORT).show();
        }

		/* Initialize application preferences */
        // this = your fragment
        mSharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);


        boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);

		/*  if already logged in, then hide login layout and show share layout */
        if (isLoggedIn) {

            String username = mSharedPreferences.getString(PREF_USER_NAME, "");
            userName.setText(getResources().getString(R.string.hello)
                    + username);

        } else {

            Uri uri = getActivity().getIntent().getData();

            if (uri != null && uri.toString().startsWith(callbackUrl)) {

                String verifier = uri.getQueryParameter(oAuthVerifier);

                try {

                    // Getting oAuth authentication token
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

                    // Getting user id form access token
                    long userID = accessToken.getUserId();
                    final User user = twitter.showUser(userID);
                    final String username = user.getName();

                    // save updated token
                    saveTwitterInfo(accessToken);

                    userName.setText(getString(R.string.hello) + username);

                } catch (Exception e) {
                    Log.e("Failed to login Twitter!!", e.getMessage());
                }
            }

        }

        //login Button Listener
        imageBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToTwitter();
            }
        });


        // Inflate the layout for this fragment
        return mView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    private void saveTwitterInfo(AccessToken accessToken) {

        long userID = accessToken.getUserId();

        User user;
        try {
            user = twitter.showUser(userID);

            String username = user.getName();

			/* Storing oAuth tokens to shared preferences */
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
            e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
            e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
            e.putString(PREF_USER_NAME, username);
            e.commit();

        } catch (TwitterException e1) {
            e1.printStackTrace();
        }
    }

    /* Reading twitter essential configuration parameters from strings.xml */
    private void initTwitterConfigs() {
        consumerKey = getString(R.string.twitter_consumer_key);
        consumerSecret = getString(R.string.twitter_consumer_secret);
        callbackUrl = getString(R.string.twitter_callback);
        oAuthVerifier = getString(R.string.twitter_oauth_verifier);
    }

    private void loginToTwitter() {
        boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
        Log.d("login", "Boolean: isLoogenIn: " + isLoggedIn);

        if (!isLoggedIn) {
            final ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(consumerKey);
            builder.setOAuthConsumerSecret(consumerSecret);

            final Configuration configuration = builder.build();
            final TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            try {


                requestToken = twitter.getOAuthRequestToken(callbackUrl);
/*
                final Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
                startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
*/
                fragmentManager.beginTransaction()
                        .replace(R.id.startLogin_layout, WebViewFragment.newInstance(requestToken.getAuthenticationURL(), "b"))
                        .commit();

                /**
                 *  Loading twitter login page on webview for authorization
                 *  Once authorized, results are received at onActivityResult
                 *
                 **/


                Toast.makeText(getActivity(), "login Try", Toast.LENGTH_LONG).show();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }
}
