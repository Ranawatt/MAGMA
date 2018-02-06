package com.magma.minemagma;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    private EditText et_email;
    private EditText et_phn;
    private ImageButton bt_signup;
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;

    private static final String REGISTER_URL = "http://minemagma.com/Service1.svc/InsertUserDetails?";

   // public static final String KEY_USERNAME = "userName";
   // public static final String KEY_PASSWORD = "password";

    private String email;
    private String phonenumber;
    private ProgressDialog progress;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_phn = (EditText) findViewById(R.id.et_phn);
        et_email = (EditText) findViewById(R.id.et_email);
        bt_signup = (ImageButton) findViewById(R.id.imageButton4);

        mSharedPreference = getSharedPreferences("Myfile",MODE_PRIVATE);
        mEditor = mSharedPreference.edit();

        context = getApplicationContext();

        if (!checkPermission())
        {
            requestPermission();
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,Manifest.permission.INTERNET)){

            // Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(RegistrationActivity.this,new String[]{Manifest.permission.INTERNET},1);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
//    private void registerUser(){
//        progress = new ProgressDialog(this);
//        progress.setTitle("Register");
//        progress.setMessage("Wait while loading...");
//        progress.show();
//        firstname = et_fname.getText().toString().trim();
//        lastname = et_lname.getText().toString().trim();
//        password = et_pswd.getText().toString().trim();
//        email = et_email.getText().toString().trim();
//        phonenumber = et_phn.getText().toString().trim();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(RegistrationActivity.this,response,Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RegistrationActivity.this,error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            protected Map<String,String> getParams(){
//
//                Map<String,String> map = new HashMap<String,String>();
//                map.put("firstName", firstname);
//                map.put("lastName", lastname);
//                map.put("email", email);
//                map.put("password", password);
//                map.put("contactNumber", phonenumber);
//                return map;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

    private void register(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", et_email.getText().toString());
        params.put("contactNumber", et_phn.getText().toString());


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //conTV.setText(response.toString());
                        Log.d("Reger", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Reger", error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        requestQueue.add(jor);
    }



    private void userRegister() {

        progress = new ProgressDialog(this);
        progress.setTitle("Register");
        progress.setMessage("Wait while loading...");
        progress.show();
        email = et_email.getText().toString().trim();
        phonenumber = et_phn.getText().toString().trim();





        String text  ="email=" + email + "&"+ "contactNumber=" + phonenumber;

        Uri.encode(text);

        text=text.replaceAll(" ", "%20");

        final String results ="Details inserted successfully";



        Map<String, String> params = new HashMap<String, String>();

        //Pass Payload as JSON object like this
        params.put("email", et_email.getText().toString());
        params.put("contactNumber", et_phn.getText().toString());


        JSONObject payload= new JSONObject(params);


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,REGISTER_URL,payload,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                //response.toString()
                openLogin();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);

        requestQueue.add(req);







/*
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

                           // if(value1.equalsIgnoreCase(results))
                            {
                                openLogin();
                            //}else{

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

                        Toast.makeText(RegistrationActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG ).show();
                       progress.dismiss();

                       // Log.d( "Error on JSON response: " + error.getMessage());

                    }
                }

        );

        requestQueue.add(jsArrayRequest);*/

//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, text,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            //If this is null, it stops here and goes to catch.
//                            String value1 = obj.getString("d");
//
//                            //Toast.makeText(RegistrationActivity.this,value1 + results,Toast.LENGTH_LONG).show();
//
//                            if(value1.equalsIgnoreCase(results))
//                            {
//                                openLogin();
//                            }else{
//
//                                progress.dismiss();
//                                //Toast.makeText(RegistrationActivity.this,response,Toast.LENGTH_LONG).show();
//                            }
//                            //Can't perform any more code because Value1 is null
//
//                        } catch (JSONException e) {
//
//                            progress.dismiss();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RegistrationActivity.this,"Please Check Internet Connection" + error,Toast.LENGTH_LONG ).show();
//                        progress.dismiss();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map = new HashMap<String,String>();
//                map.put("firstName", firstname);
//                map.put("lastName", lastname);
//                map.put("email", email);
//                map.put("password", password);
//                map.put("contactNumber", phonenumber);
//                map.put("Content-Type", "application/json; charset=utf-8");
//
//                return map;
//            }
//        };
//
//       // RequestQueue requestQueue = Volley.newRequestQueue(this);
//        queue.add(stringRequest);
    }
    private void openLogin(){
        progress.dismiss();
        AlertDialog alertDialog = new AlertDialog.Builder(
                RegistrationActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Register");

        // Setting Dialog Message
        alertDialog.setMessage("Register in MAGMA succesfully");

        // Setting Icon to Dialog


        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // commit the values

                // after saving the value open next activity
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                // Write your code here to execute after dialog closed

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void signUp(View view) {
        String email = et_email.getText().toString();
        String phn = et_phn.getText().toString();

        if (et_email.getText().length() <= 0) {
            Toast.makeText(RegistrationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
        }else if (et_phn.getText().length() <= 0) {
            Toast.makeText(RegistrationActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }

        else {

            // as now we have information in string. Lets stored them with the help of editor
//            mEditor.putString("Email", email);
//            mEditor.putString("Password", pswd);
//            mEditor.putString("First Name", fname);
//            mEditor.putString("Last Name", lname);
//            mEditor.putString("Phone Number", phn);
//            mEditor.commit();   // commit the values

            register(REGISTER_URL);

           // userRegister();

           // registerUser();

        }
    }

    public void back(View view) {
        onBackPressed();
    }
}
