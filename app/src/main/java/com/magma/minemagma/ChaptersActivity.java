package com.magma.minemagma;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChaptersActivity extends AppCompatActivity{

    private ListView lv_ch;
    private ArrayList<String> chap_name;
    private ChaptersAdapter chaptersAdapter;
    private String pdfName;


    private static final String CHAPTER_URL = "http://minemagma.com/Service1.svc/GetChapterByBookID?";
    public static final String MIME_TYPE_PDF = "application/pdf";
    private ProgressDialog progress;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        lv_ch = (ListView) findViewById(R.id.lv_chapters);

        homeDataFetch();



//        chap_name = new ArrayList<>();
//        chap_name.add(0, "Chapter I\n" +
//                "PRELIMINARY");
//        chap_name.add(1, "Chapter II\n" +
//                "GRANT OF MINING LEASE");
//        chap_name.add(2, "Chapter III\n" +
//                "GRANT OF COMPOSITE LICENCE");
//        chap_name.add(3, "Chapter IV\n" +
//                "MISCELLANEOUS");

//        chaptersAdapter = new ChaptersAdapter(ChaptersActivity.this, chap_name);
//        lv_ch.setAdapter(chaptersAdapter);
//        lv_ch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                chaptersAdapter.notifyDataSetChanged();
//            }
//        });
        context = getApplicationContext();


    }
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission1(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(ChaptersActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            Toast.makeText(context,"Read permission allows us to access pdf. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ChaptersActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
    }

    private boolean checkPermission1(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(ChaptersActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            Toast.makeText(context,"External Permission allows us to access pdf. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ChaptersActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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

                            if (!checkPermission1())
                            {
                                requestPermission1();
                            }
                            else
                            {
                                copyFileAssets();
                                showPdf();
                            }

                        }
                    }).start();

                } else {

                    Toast.makeText(ChaptersActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {




                    new Thread(new Runnable() {
                        public void run() {

                            copyFileAssets();
                            showPdf();

                        }
                    }).start();

                } else {

                    Toast.makeText(ChaptersActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
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
        String title = extras.getString("booksIDS");

        String text  = CHAPTER_URL + "bookID=" + title;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, text,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            //If this is null, it stops here and goes to catch.
                            JSONArray books = obj.getJSONArray("d");

                            final ArrayList<String> chap_name = new ArrayList<String>();

                            final ArrayList<String> chap_ID = new ArrayList<String>();

                            books = sortArray(books,"ChaphterId");

                            for (int i=0; i<books.length(); i++) {
                                JSONObject json_data = books.getJSONObject(i);
                                chap_name.add(json_data.getString("ChaphterName"));
                                chap_ID.add(json_data.getString("ChaphterId"));//add to arraylist
                            }

//if you want your array
                          //  String [] web = stringArrayList.toArray(new String[stringArrayList.size()]);




                            chaptersAdapter = new ChaptersAdapter(ChaptersActivity.this, chap_name);


                        //    lv_ch.setChoiceMode(lv_ch.CHOICE_MODE_MULTIPLE);
                            lv_ch.setAdapter(chaptersAdapter);

                          //  Utility.setListViewHeightBasedOnChildren(lv_ch);
                            lv_ch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    chaptersAdapter.notifyDataSetChanged();
                                     String chapterID = chap_ID.get(i);

                                    if(chapterID.equals("208"))
                                    {
                                        pdfName = "onescheduleone.pdf";
                                        pdfreaders();
//                                        copyFileAssets();
//                                        showPdf();
                                    }
                                    else if(chapterID.equals("209"))
                                    {
                                        pdfName = "onescheduletwo.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("210"))
                                    {
                                        pdfName = "oneschedulethree.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("211"))
                                    {
                                        pdfName = "oneschedulefour.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("307"))
                                    {
                                        pdfName = "twoscheduleone.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("308"))
                                    {
                                        pdfName = "twoscheduletwo.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("309"))
                                    {
                                        pdfName = "twoschedulethree.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("310"))
                                    {
                                        pdfName = "twoschedulefour.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("618"))
                                    {
                                        pdfName = "fivescheduleone.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("619"))
                                    {
                                        pdfName = "fivescheduletwo.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("620"))
                                    {
                                        pdfName = "fiveschedulethree.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("621"))
                                    {
                                        pdfName = "fiveschedulefour.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("622"))
                                    {
                                        pdfName = "fiveschedulefive.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("623"))
                                    {
                                        pdfName = "fiveschedulesix.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("624"))
                                    {
                                        pdfName = "fivescheduleseven.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("625"))
                                    {
                                        pdfName = "fivescheduleeight.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("626"))
                                    {
                                        pdfName = "fiveschedulenine.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("627"))
                                    {
                                        pdfName = "sixscheduleten.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("628"))
                                    {
                                        pdfName = "fivescheduleellevan.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("705"))
                                    {
                                        pdfName = "sixscheduleone.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("706"))
                                    {
                                        pdfName = "sixscheduletwo.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("707"))
                                    {
                                        pdfName = "sixschedulethree.pdf";
                                        pdfreaders();
                                    }
                                    else if(chapterID.equals("802"))
                                    {
                                        pdfName = "sevenscheduleone.pdf";
                                        pdfreaders();
                                    }
                                    else {

                                        Intent intent = new Intent(getApplicationContext(), SectionActivity.class);
                                        intent.putExtra("chapIDS", chap_ID.get(i));
                                        startActivity(intent);
                                    }

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
                        Toast.makeText(ChaptersActivity.this,"Please Check Internet Connection", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private  void pdfreaders()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            context = getApplicationContext();

            if (!checkPermission())
            {
                requestPermission();
            }
            else
            {
                new Thread(new Runnable() {
                    public void run() {

                        copyFileAssets();
                        showPdf();
                    }
                }).start();
            }




        }
        else
        {
            new Thread(new Runnable() {
                public void run() {

                    copyFileAssets();
                    showPdf();
                }
            }).start();
        }
    }
    private void copyFileAssets() {
        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(pdfName);
            String extStorageDirectory = Environment.getExternalStorageDirectory()
                    .toString();
            File folder = new File(extStorageDirectory, "MAGMA");
            folder.mkdir();
            out = new FileOutputStream(folder+File.separator+ pdfName);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch(IOException e) {
            Log.e("tag", "Failed to copy asset file: " + pdfName, e);
        }

    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    public void showPdf()
    {
        progress.dismiss();

//        File sdCard = Environment.getExternalStorageDirectory();
//        File directory = new File (sdCard, "MyFiles");
//        File file = new File(directory, filename);
//        try{
//            FileInputStream fIn = new FileInputStream(file);
//            DataInputStream is = new DataInputStream(fIn);
//            String name = is.readUTF();
//            String content = is.readUTF();
//            is.close();
//        } catch (FileNotFoundException e) {
//            // handle exception
//        } catch (IOException e) {
//            // handle exception
//        }

        File pdfFile = new File(Environment.getExternalStorageDirectory()+"/MAGMA/" + pdfName);//File path
        if (pdfFile.exists()) //Checking for the file is exist or not
        {
            //PackageManager packageManager = getPackageManager();
            // Intent testIntent = new Intent(Intent.ACTION_VIEW);
            /// testIntent.setType("application/pdf");
            //List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        } else {

            Toast.makeText(ChaptersActivity.this, "The file not exists! ", Toast.LENGTH_SHORT).show();

        }



    }
    public static boolean canDisplayPdf(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType(MIME_TYPE_PDF);
        if (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chapters, menu);
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

//    public void chapterI(View view) {
//        Intent intent = new Intent(this, SectionActivity.class);
//        startActivity(intent);
//    }

    public void back(View view) {
        onBackPressed();
    }
}
