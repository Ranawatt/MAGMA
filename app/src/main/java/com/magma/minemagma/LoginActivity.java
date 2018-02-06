package com.magma.minemagma;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener  {

    private EditText et_email;
    private EditText et_pswd;
    private ImageButton bt_login;
    private ImageButton bt_fb;
   // private ImageButton bt_google;
    private TextView tv_crAcc;

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private ImageButton btnSignIn;

    String loginUserName;


//    private SharedPreferences mSharedPreference;
//    private SharedPreferences.Editor mEditor;

    private ProgressDialog pDialog;
    private JSONObject json;
    private int success = 0;
    private HTTPURLConnection service;

    private String passwordSocial;
    private String firstnameSocial;
    private String lastnameSocial;
    private String emailSocial;
    private String phonenumberSocial;



    //Initialize webservice URL
    private String path = "http://minemagma.com/Service1.svc/AuthenticateUser?";
    private String email, pswd;

    private static final String LOGIN_URL = "http://minemagma.com/Service1.svc/AuthenticateUser?";
    private static final String REGISTER_URL = "http://minemagma.com/Service1.svc/InsertUserDetails?";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "password";
    private String username;
    private String password;
    private ProgressDialog progress;

    public static final String PREFS_NAME = "MyPrefsFile";
    private Context context;

    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize SDK before setContentView(Layout ID)
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = (EditText) findViewById(R.id.et_email);
        et_pswd = (EditText) findViewById(R.id.et_pswd);
        bt_login = (ImageButton) findViewById(R.id.imageButton);
        btnSignIn = (ImageButton) findViewById(R.id.imageButton3);
        tv_crAcc = (TextView) findViewById(R.id.tv_createAcc);
        bt_fb = (ImageButton) findViewById(R.id.imageButton2);

        context = getApplicationContext();

//        if (!checkPermission())
//        {
//            requestPermission();
//        }

        btnSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
       // btnSignIn.setSize(SignInButton.SIZE_STANDARD);
       // btnSignIn.setScopes(gso.getScopeArray());


        bt_fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call private method
                onFblogin();
            }
        });

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String firstName = acct.getGivenName();
            String lastName = acct.getFamilyName();
            //String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            if (firstName != null && !firstName.isEmpty() && !firstName.equals("null"))
            {

            }
            else
            {
                firstName = "";
            }
            if (lastName != null && !lastName.isEmpty() && !lastName.equals("null"))
            {

            }
            else
            {
                lastName = "";
            }
            if (email != null && !email.isEmpty() && !email.equals("null"))
            {

            }
            else
            {
                email = "test" + firstName + "@magma.com";;
            }

            Log.e(TAG, "Name: " + firstName + ", email: " + email
                    + ", Image: " + lastName);

            firstnameSocial = firstName;
            lastnameSocial = lastName;
            emailSocial = email;

           // txtName.setText(personName);
           // txtEmail.setText(email);

            registrationPress();
           // updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imageButton3:
                signIn();
                break;


        }
    }
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
           // showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

      //  mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    // Private method to handle Facebook login and callback
    private void onFblogin()
    {
        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();
                       // final FBUser fbUser = new FBUser();
                        GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                                if (graphResponse.getError() != null) {
                                    // handle error
                                    System.out.println("ERROR");
                                } else
                                {
                                    System.out.println("Success");

                                    String jsonresult = String.valueOf(json);
                                    System.out.println("JSON Result"+jsonresult);

                                    String str_email = user.optString("email");
                                    String str_id = user.optString("id");
                                    String name = user.optString("name");

                                    String str_firstname = "";
                                    String str_lastname= "";
                                    if(name.split("\\w+").length>1){

                                        str_lastname = name.substring(name.lastIndexOf(" ")+1);
                                        str_firstname = name.substring(0, name.lastIndexOf(' '));
                                    }
                                    else{
                                        str_firstname = name;
                                        str_lastname ="";
                                    }
                                    if (str_lastname != null && !str_lastname.isEmpty() && !str_lastname.equals("null"))
                                    {

                                    }
                                    else
                                    {
                                        str_lastname = "";
                                    }
                                    if (str_firstname != null && !str_firstname.isEmpty() && !str_firstname.equals("null"))
                                    {

                                    }
                                    else
                                    {
                                        str_firstname = "";
                                    }



                                    if (str_email != null && !str_email.isEmpty() && !str_email.equals("null"))
                                    {

                                    }
                                    else
                                    {
                                        str_email = "test" + str_firstname + "@magma.com";
                                    }

                                    firstnameSocial = str_firstname;
                                    lastnameSocial = str_lastname;
                                    emailSocial = str_email;


                                    registrationPress();


                                }
                            }
                        }).executeAsync();
                    }

//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//
//                        System.out.println("Success");
//                        GraphRequest.newMeRequest(
//                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(JSONObject json, GraphResponse response) {
//                                        if (response.getError() != null) {
//                                            // handle error
//                                            System.out.println("ERROR");
//                                        } else {
//                                            System.out.println("Success");
//                                            try {
//
//                                                String jsonresult = String.valueOf(json);
//                                                System.out.println("JSON Result"+jsonresult);
//
//                                                String str_email = json.getString("email");
//                                                String str_id = json.getString("id");
//                                                String str_firstname = json.getString("first_name");
//                                                String str_lastname = json.getString("last_name");
//
//                                                if (str_email != null && !str_email.isEmpty() && !str_email.equals("null"))
//                                                {
//                                                    str_email = str_id + "@test.com";
//                                                }
//
//                                                firstnameSocial = str_firstname;
//                                                lastnameSocial = str_lastname;
//                                                emailSocial = str_email;
//
//
//
//                                                registrationPress();
//
//
//
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                               // progress.dismiss();
//                                            }
//                                        }
//                                    }
//
//                                }).executeAsync();

//                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this,"cancel",Toast.LENGTH_LONG).show();
                       // progress.dismiss();
                    }

                    @Override
                    public void onError(FacebookException error) {
                      //  progress.dismiss();
                       // Toast.makeText(LoginActivity.this,error,Toast.LENGTH_LONG).show();
                        //Log.d(TAG_ERROR,error.toString());
                    }
                });
    }

    private void registrationPress()
    {
        progress = new ProgressDialog(this);
        progress.setTitle("Login");
        progress.setMessage("Wait while loading...");
        progress.show();

        String text  ="firstName=" + firstnameSocial + "&"+ "lastName=" + lastnameSocial + "&"+ "email=" + emailSocial + "&"+ "password=" + "123456" + "&"+ "contactNumber=" + "123456789";

        Uri.encode(text);

        text=text.replaceAll(" ", "%20");

        final String results = firstnameSocial +" " + "Details inserted successfully";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                REGISTER_URL + text,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // JSONObject obj = new JSONObject(response);
                            //If this is null, it stops here and goes to catch.
                            String value1 = response.getString("d");

                            //Toast.makeText(RegistrationActivity.this,value1 + results,Toast.LENGTH_LONG).show();

                            if(value1.equalsIgnoreCase(results))
                            {


                                loginUserName = firstnameSocial + lastnameSocial;

                                openProfile();
                            }else{

                                progress.dismiss();
                                //Toast.makeText(RegistrationActivity.this,response,Toast.LENGTH_LONG).show();
                            }
                            //Can't perform any more code because Value1 is null

                        } catch (JSONException e) {

                            progress.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG ).show();
                        progress.dismiss();

                        // Log.d( "Error on JSON response: " + error.getMessage());

                    }
                }
        );

        requestQueue.add(jsArrayRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else {

            callbackmanager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,Manifest.permission.INTERNET)){

           // Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.INTERNET},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new Thread(new Runnable() {
                        public void run() {

                        }
                    }).start();

                } else {

                   // Toast.makeText(LoginActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }


    private void userLogin() {

        progress = new ProgressDialog(this);
        progress.setTitle("Login");
        progress.setMessage("Wait while loading...");
        progress.show();

        username = et_email.getText().toString().trim();
        password = et_pswd.getText().toString().trim();

        String text = LOGIN_URL + "userName=" + username + "&" + "password=" + password;

        Uri.encode(text);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, text,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            //If this is null, it stops here and goes to catch.
                            String value1 = obj.getString("d");

                            if (value1.equalsIgnoreCase(" LogIn successfully")) {


                                loginUserName = username;

                                openProfile();
                            } else {
                                //Toast.makeText(RegistrationActivity.this,response,Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                            //Can't perform any more code because Value1 is null

                        } catch (JSONException e) {

                            progress.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"Please Check Internet Connection", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, username);
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put(KEY_PASSWORD, password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void openProfile() {
        Intent intent = new Intent(this, MenuslideActivity.class);

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=saved_values.edit();
        editor.putBoolean("Islogin", true);
        editor.putString("loginUSERS",loginUserName);
        editor.commit();
        progress.dismiss();
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createAccount(View view) {

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        userLogin();
    }



}
