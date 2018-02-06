package com.magma.minemagma;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeActivity extends AppCompatActivity
{
    GridView grid;


    private static final String HOME_URL = "http://minemagma.com/Service1.svc/GetAllBookList";
    private CustomGrid adapter;
    private ProgressDialog progress;
    private Context context;
   // private TextView profilename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


         homeDataFetch();


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

        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,Manifest.permission.INTERNET)){

            // Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.INTERNET},1);
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

    public static JSONArray sortArray(JSONArray jsonArr, final String sortBy){
        JSONArray sortedJsonArray = new JSONArray();
        try
        {
            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < jsonArr.length(); i++) {
                jsonValues.add(jsonArr.getJSONObject(i));
            }
            Collections.sort( jsonValues, new Comparator<JSONObject>() {

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    Integer valA = 0;// = new String();
                    Integer valB = 0;// = new String();

                    try {
                        valA = (Integer) a.get(sortBy);
                        valB = (Integer) b.get(sortBy);

                        // valA = String.valueOf(valA1);
                        // valA = String.valueOf(valB1);
                    }
                    catch (JSONException e) {
                        //do something
                    }

                    return valA.compareTo(valB);
                    //if you want to change the sort order, simply use the following:
                    //return -valA.compareTo(valB);
                }
            });

            for (int i = 0; i < jsonArr.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return sortedJsonArray;
    }
    private void homeDataFetch()
    {
        progress = new ProgressDialog(this);
        progress.setTitle("");
        progress.setMessage("Wait while loading...");
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, HOME_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            //If this is null, it stops here and goes to catch.
                            JSONArray books = obj.getJSONArray("d");

                            ArrayList<String> stringArrayList = new ArrayList<String>();
                            ArrayList<String> stringArrayID = new ArrayList<String>();

                            books=sortArray(books,"BookId");

                            for (int i=0; i<books.length(); i++) {
                                JSONObject json_data = books.getJSONObject(i);
                                stringArrayList.add(json_data.getString("BookTittle"));
                                stringArrayID.add(json_data.getString("BookId"));//add to arraylist
                            }

//if you want your array
                            final String [] web = stringArrayList.toArray(new String[stringArrayList.size()]);
                            final String [] bookValues = stringArrayID.toArray(new String[stringArrayID.size()]);

//                            for (int i=0; i<books.length(); i++) {
//                                JSONObject json_data = books.getJSONObject(i);
//                                web[i] = json_data.getString("BookTittle");
//                                web = books.toArray(new String[books.size()]);
//                            }



                            adapter = new CustomGrid(HomeActivity.this, web);
                            grid=(GridView)findViewById(R.id.grid);
                            grid.setAdapter(adapter);
                            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {

                                    Intent intent = new Intent(getApplicationContext(), ChaptersActivity.class);
                                    intent.putExtra("booksIDS", bookValues[position]);
                                    startActivity(intent);

                                    //Toast.makeText(HomeActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

                                }
                            });

                            progress.dismiss();

                            //Can't perform any more code because Value1 is null

                        } catch (JSONException e) {

                            progress.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG ).show();
                        progress.dismiss();
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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


//    public void next(View view) {
//        Intent intent = new Intent(this, ChaptersActivity.class);
//        startActivity(intent);
//    }

    public void back(View view) {
        onBackPressed();
    }

}
