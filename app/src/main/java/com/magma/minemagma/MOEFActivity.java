package com.magma.minemagma;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MOEFActivity extends AppCompatActivity {

    GridView grid;
    String[] web = {
            "25 Feb 2016","3 March 2016","9 March 2016","16 March 2016","31 March 2016","20 June 2016","30 Aug 2016","29 Sep 2016","30 Sep 2016","24 Oct 2016"
    };
    String[] PDFNames = {
            "p25feb2016moef.pdf","p3march2016moef.pdf","p9march2016moef.pdf","p16march2016moef.pdf","p31march2016moef.pdf","p20june2016moef.pdf","p30aug2016moef.pdf","p29sep2016moef.pdf","p30sep2016moef.pdf","p24Oct2016moef.pdf"
    };
    private Button btn;
    private MoefAdapter adapter;
    private LinearLayout lp;
    private TextView uploadText;

    private String pdfName;
    private ProgressDialog progress;
    private Context context;

    public static final String MIME_TYPE_PDF = "application/pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moef);

        lp = (LinearLayout)findViewById(R.id.bg_image);



        adapter = new MoefAdapter(MOEFActivity.this, web);
        grid=(GridView)findViewById(R.id.grid);
        uploadText=(TextView)findViewById(R.id.upload);
        btn = (Button) findViewById(R.id.search_btn);
        uploadText.setVisibility(View.GONE);

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {



                pdfName = PDFNames[position];

                progress = new ProgressDialog(MOEFActivity.this);
                progress.setTitle("");
                progress.setMessage("Wait while loading...");
                progress.show();



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



                // showPdf();

//                Intent intent = new Intent(view.getContext(), Reader.class);
//                intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME,"/sdcard0/cadastralmap.pdf");
//                startActivity(intent);
//                Intent intent = new Intent(view.getContext(), Reader.class);
//                startActivity(intent);
            }
        });
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(MOEFActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            Toast.makeText(context,"Read permission allows us to access pdf. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MOEFActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(MOEFActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            Toast.makeText(context,"External Permission allows us to access pdf. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MOEFActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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

                    Toast.makeText(MOEFActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(MOEFActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }


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
            out = new FileOutputStream(folder+File.separator+ "magmapdf.pdf");
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch(IOException e) {
            Log.e("tag", "Failed to copy asset file: " + "magmapdf.pdf", e);
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
        boolean IsPdf = canDisplayPdf(context);
        if (IsPdf) {

            File file = new File(Environment.getExternalStorageDirectory()+"/MAGMA/" + "magmapdf.pdf");
            PackageManager packageManager = getPackageManager();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");
            List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0 && file.isFile()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/pdf");
                startActivity(intent);
            }
        }
        else
        {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Your dialog code.
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MOEFActivity.this);
                    builder.setMessage("Pdf reader not installed in your device")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {




                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            //           Toast.makeText(MOEFActivity.this, "Pdf reader not installed in your device", Toast.LENGTH_SHORT).show();

//            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Pdf reader not installed in your device")
//                    .setCancelable(false)
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//
//
//
//                        }
//                    });
//
//            AlertDialog alert = builder.create();
//            alert.show();
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

    public void search(View view)
    {
        final CharSequence[] items = {

                "2012","2013","2014","2015","2016"
        };

        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                btn.setText(items[i]);

                if (items[i] == "2016") {
                    String[] web = {
                            "25 Feb 2016","3 March 2016","9 March 2016","16 March 2016","31 March 2016","20 June 2016","30 Aug 2016","29 Sep 2016","30 Sep 2016","24 Oct 2016"
                    };

                    adapter = new MoefAdapter(MOEFActivity.this,web );
                    grid.invalidateViews();
                    grid.setAdapter(adapter);
                    uploadText.setVisibility(View.GONE);

                } else

                {
                    String[] web = {

                    };
                    adapter = new MoefAdapter(MOEFActivity.this,web );
                    grid.invalidateViews();
                    grid.setAdapter(adapter);
                    uploadText.setVisibility(View.VISIBLE);

                }

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
