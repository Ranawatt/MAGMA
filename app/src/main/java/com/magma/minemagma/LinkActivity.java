package com.magma.minemagma;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;


public class LinkActivity extends AppCompatActivity  {

    GridView grid;
    String[] web = {"http://mines.nic.in/","http://ibm.nic.in/","http://envfor.nic.in/","http://supremecourtofindia.nic.in/","http://orissaminerals.gov.in/website/defaultnew.aspx","http://khanija.kar.ncode.in/SitePages/Home.aspx","http://www.dmg-raj.org/","http://www.aranya.gov.in/Home.aspx","http://cpcb.nic.in/","http://kspcb.gov.in/","http://ospcboard.org/"};
    private WebView webView;
    private LinkAdapter linkAdapter;
    private int position;
    private RelativeLayout layout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);


        grid = (GridView) findViewById(R.id.grid);
        layout = (RelativeLayout) findViewById(R.id.relative5);
        //layout.setTag();

        linkAdapter = new LinkAdapter(LinkActivity.this, web);
        grid.setAdapter(linkAdapter);

//        webView = new WebView(LinkActivity.this);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.loadUrl("http://www.google.com");
//        setContentView(webView);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               // int tagValue = (Integer)view.getTag();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(web[i]));
                startActivity(intent);

            }
        });
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(LinkActivity.this,Manifest.permission.INTERNET)){

            // Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(LinkActivity.this,new String[]{Manifest.permission.INTERNET},1);
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


//    public void browser(View view) {
//
//        int tagValue = (Integer)view.getTag();
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(web[tagValue]));
//        startActivity(intent);
//    }
}