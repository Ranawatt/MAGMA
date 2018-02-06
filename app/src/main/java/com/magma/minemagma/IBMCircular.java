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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class IBMCircular extends AppCompatActivity {

    private ListView lv_sec;
    String[] ibm_name = {"45 Registration", "Approval and Modification of Mining Plan and Scheme",
            "Cadastral Map", "Circular 5 of 2013", "Implementation of Rule 45 of MCDR 1988",
            "Order Mining Plan", "Threshold", "To RQP"};
    String[] savePDF_name = {"p45registration.pdf", "approvalandmodificationofminingplanandscheme.pdf",
            "cadastralmap.pdf", "circular5of2013.pdf", "implementationofrule45ofmcdr1988.pdf",
            "orderminingplan.pdf", "threshold.pdf", "torqp.pdf"};
    private IBMAdapter ibmAdapeter;
    private Context context;
    private String thedata;
    private String mydate;

    public static final String MIME_TYPE_PDF = "application/pdf";

   // private String filename = "cadastralmap.pdf";
   // private String filepath = "MyFileStorage";
    File myInternalFile;
    File myExternalFile;
    private String pdfName;
    private ProgressDialog progress;

    public int getPreviousPageImageResource() { return R.drawable.left_arrow; }
    public int getNextPageImageResource() { return R.drawable.right_arrow; }
    public int getZoomInImageResource() { return R.drawable.zoom_in; }
    public int getZoomOutImageResource() { return R.drawable.zoom_out; }
    public int getPdfPasswordLayoutResource() { return R.layout.pdf_file_password; }
    public int getPdfPageNumberResource() { return R.layout.dialog_pagenumber; }
    public int getPdfPasswordEditField() { return R.id.etPassword; }
    public int getPdfPasswordOkButton() { return R.id.btOK; }
    public int getPdfPasswordExitButton() { return R.id.btExit; }
    public int getPdfPageNumberEditField() { return R.id.pagenum_edit; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibmcircular);

        lv_sec = (ListView) findViewById(R.id.lv_section);

        ibmAdapeter = new IBMAdapter(IBMCircular.this, ibm_name);
        lv_sec.setAdapter(ibmAdapeter);
        lv_sec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ibmAdapeter.notifyDataSetChanged();

                 pdfName = savePDF_name[i];



//                ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
//                File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
//                myInternalFile = new File(directory , filename);
//
//                try {
//                    FileOutputStream fos = new FileOutputStream(myInternalFile);
//                    fos.write(myInputText.getText().toString().getBytes());
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                FileInputStream fis = new FileInputStream(myInternalFile);
//                DataInputStream in = new DataInputStream(fis);
//                BufferedReader br =
//                        new BufferedReader(new InputStreamReader(in));
//                String strLine;
//                while ((strLine = br.readLine()) != null) {
//                    myData = myData + strLine;
//                }
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//                File sdCard = Environment.getExternalStorageDirectory();
//                File directory = new File (sdCard, "MAGMA");
//                directory.mkdirs();
//                File file = new File(directory, "cadastralmap.pdf");
//                try {
//                    FileOutputStream fOut = new FileOutputStream(file);
//                    DataOutputStream os = new DataOutputStream(fOut);
//                    os.writeUTF(thedata);
//                    os.writeUTF(mydate);
//                    os.close();
//                } catch (FileNotFoundException e) {
//                    // handle exception
//                } catch (IOException e) {
//                    // handle exception
//                }

//                String extStorageDirectory = Environment.getExternalStorageDirectory()
//                        .toString();
//                File folder = new File(extStorageDirectory, "MAGMA");
//                folder.mkdir();
//                File file = new File(folder, "cadastralmap.pdf");
//                try {
//                    file.createNewFile();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }

                // prepare for a progress bar dialog
                progress = new ProgressDialog(IBMCircular.this);
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(IBMCircular.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            Toast.makeText(context,"Read permission allows us to access pdf. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(IBMCircular.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},2);
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(IBMCircular.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            Toast.makeText(context,"External Permission allows us to access pdf. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(IBMCircular.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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

                    Toast.makeText(IBMCircular.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(IBMCircular.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
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
                    final AlertDialog.Builder builder = new AlertDialog.Builder(IBMCircular.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ibmcircular, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
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

}