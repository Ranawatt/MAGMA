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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class SectionActivity extends AppCompatActivity {

    private ListView lv_sec;
    private SectionAdapter sectionAdapter;

    private static final String SECTION_URL = "http://minemagma.com/Service1.svc/GetSectionListByChapterId?";
    private ProgressDialog progress;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        lv_sec = (ListView) findViewById(R.id.lv_section);

        homeDataFetch();

//        sec_name = new ArrayList<>();
//        sec_name.add(0, "Section 1");
//        sec_name.add(1, "Section 2");
//        sec_name.add(2, "Section 3");

//        sectionAdapter = new SectionAdapter(SectionActivity.this, sec_name);
//        lv_sec.setAdapter(sectionAdapter);
//        lv_sec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                sectionAdapter.notifyDataSetChanged();
//                Intent intent = new Intent(view.getContext(), ReaderActivity.class);
//
//                startActivity(intent);
//            }
//        });
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(SectionActivity.this,Manifest.permission.INTERNET)){

            // Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(SectionActivity.this,new String[]{Manifest.permission.INTERNET},1);
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

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("chapIDS");

        String text  = SECTION_URL + "chapterID=" + title;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, text,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            //If this is null, it stops here and goes to catch.
                            JSONArray books = obj.getJSONArray("d");

                            final ArrayList<String> sec_name = new ArrayList<String>();
                            final ArrayList<String> sec_ID = new ArrayList<String>();
                            final ArrayList<String> sec_text = new ArrayList<String>();

                            books = sortArray(books,"SectionId");

//                            //sortJsonArray(books);
//
//                           // JSONArray jsonArr = new JSONArray(obj.get);
//                            JSONArray sortedJsonArray = new JSONArray();
//
//                            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
//                            for (int i = 0; i < books.length(); i++) {
//                                jsonValues.add(books.getJSONObject(i));
//                            }
//                            Collections.sort( jsonValues, new Comparator<JSONObject>() {
//                                //You can change "Name" with "ID" if you want to sort by ID
//                                private static final String KEY_NAME = "SectionId";
//
//                                @Override
//                                public int compare(JSONObject a, JSONObject b) {
//                                    String valA = new String();
//                                    String valB = new String();
//
//                                    try {
//                                        valA = (String) a.get(KEY_NAME);
//                                        valB = (String) b.get(KEY_NAME);
//                                    }
//                                    catch (JSONException e) {
//                                        //do something
//                                    }
//
//                                    return valA.compareTo(valB);
//                                    //if you want to change the sort order, simply use the following:
//                                    //return -valA.compareTo(valB);
//                                }
//                            });
//
//                            for (int i = 0; i < books.length(); i++) {
//                                sortedJsonArray.put(jsonValues.get(i));
//                            }



                            for (int i=0; i<books.length(); i++) {
                                JSONObject json_data = books.getJSONObject(i);
                                sec_name.add(json_data.getString("SectionTittle"));
                                sec_ID.add(json_data.getString("SectionId"));
                                sec_text.add(json_data.getString("SectionText"));
                                //add to arraylist

                            }

//if you want your array
                            //  String [] web = stringArrayList.toArray(new String[stringArrayList.size()]);


                            sectionAdapter = new SectionAdapter(SectionActivity.this, sec_name);
                            lv_sec.setAdapter(sectionAdapter);
                        //    Utility.setListViewHeightBasedOnChildren(lv_sec);
                            lv_sec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    sectionAdapter.notifyDataSetChanged();
                                    Intent intent = new Intent(view.getContext(), ReaderActivity.class);
                                    intent.putExtra("sectionIndex", i);
                                    intent.putExtra("secNamesArr", sec_name);
                                    intent.putExtra("secIDsArr", sec_ID);
                                    intent.putExtra("secTextsArr", sec_text);
                                    startActivity(intent);
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
                        Toast.makeText(SectionActivity.this, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_section, menu);
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

    public void back(View view) {
        onBackPressed();
    }

    public void section1(View view) {

    }
}
