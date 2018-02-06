package com.magma.minemagma;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.magma.minemagma.util.IabHelper;
import com.magma.minemagma.util.IabResult;
import com.magma.minemagma.util.Inventory;
import com.magma.minemagma.util.Purchase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

//import com.android.vending.billing.IabHelper;
//import com.android.vending.billing.IabResult;
//import com.android.vending.billing.Inventory;
//import com.android.vending.billing.Purchase;



public class ReaderActivity extends AppCompatActivity  {

    static final String TAG = "MAGMA";
    boolean mSubscribedToInfiniteGas = false;
    static final String SKU_GAS = "com.magma.magma.extrafeatures";
    static final int RC_REQUEST = 10001;


    // The helper object
    IabHelper mHelper;


    private static String files;
    String text;
    TextView et;
    TextToSpeech tts;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int counter;
    private android.content.Context context;
    private TextView headerText;
    private File file;
    private Bitmap bmp;



    public static final String DEST = "results/MAGMA/MAGMA_watermarked.pdf";
    public static final String IMG = "drawable://" + R.drawable.newmineslogo;
    private String shareString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        et = (TextView) findViewById(R.id.tv);

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "saveimage");
        file.mkdirs();

        headerText = (TextView) findViewById(R.id.readerheader);

        Bundle extras = getIntent().getExtras();
        int value = extras.getInt("sectionIndex");

        nextButton = (ImageButton) findViewById(R.id.ib5);

        prevButton = (ImageButton) findViewById(R.id.ib1);

        et.setMovementMethod(new ScrollingMovementMethod());

        ArrayList<String> textList = (ArrayList<String>) getIntent().getSerializableExtra("secTextsArr");
        ArrayList<String> titleList = (ArrayList<String>) getIntent().getSerializableExtra("secNamesArr");

        final String[] readerTextArr = textList.toArray(new String[textList.size()]);
        final String[] readerTitleArr = titleList.toArray(new String[titleList.size()]);

        counter = value;//If initially the text view is set to Monday

        et.setText(readerTextArr[counter]);
        headerText.setText(readerTitleArr[counter]);

        nextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onPause();

                counter++;

//If counter has come to end of array, set it to the first item.

                if (counter == readerTextArr.length) counter = 0;

                et.setText(readerTextArr[counter]);
                headerText.setText(readerTitleArr[counter]);


            }

        });

        prevButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onPause();

                counter--;

//If counter has come to starting of array, set it to the last item.

                if (counter < 0) counter = readerTextArr.length - 1;

                et.setText(readerTextArr[counter]);
                headerText.setText(readerTitleArr[counter]);


            }

        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //prefs.edit().putBoolean("IsPurchase", true).commit();
        boolean Ispurchase = prefs.getBoolean("IsPurchase", false);


//        if (Ispurchase) {
//
//
//
//        }
//        else {
//
//            String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtA+8HUeE6Hx1blyJW5eHKdvkDZ/DhJ+udbvqfDKG90UvlOFY/+MuZ0QIoNaHdNOUQGP61cq2L16r85KS0F+pkLVXTQ4Y0qrOZpaU8c2jXbyz8iIL41c+lNr6C2SAZ67akbpU6Nkj8MxiG3oxN2hxjS22hGBPbYNaWxb5DPkqUmRYspKW3Mq/Lg4cRjYr/DWm280ru4OuwPP7MjeJ1POMFHEf6PdHtXNhOeCsUPWczQKAIheaF28LHBmxT34zzVN//Up5kkM0g0ZdPKDuCRTFMRb2XoLlVHaJ2Y7ePo4ITCT6hLV6ZGIFvUoqBTC/N8fcba+FdLJbqxTX0WU3hV2b/wIDAQAB";
//
//
//            // Create the helper, passing it our context and the public key to verify signatures with
//            Log.d(TAG, "Creating IAB helper.");
//            mHelper = new IabHelper(this, base64EncodedPublicKey);
//
//            // enable debug logging (for a production application, you should set this to false).
//            mHelper.enableDebugLogging(true);
//
//            // Start setup. This is asynchronous and the specified listener
//            // will be called once setup completes.
//            Log.d(TAG, "Starting setup.");
//            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//                public void onIabSetupFinished(IabResult result) {
//                    Log.d(TAG, "Setup finished.");
//
//                    if (!result.isSuccess()) {
//                        // Oh noes, there was a problem.
//                        complain("Problem setting up in-app billing: " + result);
//                        return;
//                    }
//
//                    // Have we been disposed of in the meantime? If so, quit.
//                    if (mHelper == null) return;
//
//                    // IAB is fully set up. Now, let's get an inventory of stuff we own.
//                    Log.d(TAG, "Setup successful. Querying inventory.");
//                    mHelper.queryInventoryAsync(mGotInventoryListener);
//                }
//            });
//        }



    }

    //in app

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: ");
                return;
            }

            Log.d(TAG, "Query inventory was successful.");


            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase gasPurchase = inventory.getPurchase(SKU_GAS);
            if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                Log.d(TAG, "We have gas. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_GAS), mConsumeFinishedListener);
                return;
            }


            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // User clicked the "Buy Gas" button
    public void onBuyGasButtonClicked(View arg0) {
        //Log.d(TAG, "Buy gas button clicked.");

        if (mSubscribedToInfiniteGas) {
            //complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";

        mHelper.launchPurchaseFlow(this, SKU_GAS, RC_REQUEST,
                mPurchaseFinishedListener, payload);
    }

//    // User clicked the "Upgrade to Premium" button.
//    public void onUpgradeAppButtonClicked(View arg0) {
//        Log.d(TAG, "Upgrade button clicked; launching purchase flow for upgrade.");
//
//
//        /* TODO: for security, generate your payload here for verification. See the comments on
//         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
//         *        an empty string, but on a production app you should carefully generate this. */
//        String payload = "";
//
////        mHelper.launchPurchaseFlow(this, SKU_PREMIUM, RC_REQUEST,
////                mPurchaseFinishedListener, payload);
//    }
//
//    // "Subscribe to infinite gas" button clicked. Explain to user, then start purchase
//    // flow for subscription.
//    public void onInfiniteGasButtonClicked(View arg0) {
//        if (!mHelper.subscriptionsSupported()) {
//            complain("Subscriptions not supported on your device yet. Sorry!");
//            return;
//        }
//
//        /* TODO: for security, generate your payload here for verification. See the comments on
//         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
//         *        an empty string, but on a production app you should carefully generate this. */
//        String payload = "";
//
//
//        Log.d(TAG, "Launching purchase flow for infinite gas subscription.");
////        mHelper.launchPurchaseFlow(this,
////                SKU_INFINITE_GAS, IabHelper.ITEM_TYPE_SUBS,
////                RC_REQUEST, mPurchaseFinishedListener, payload);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: ");

                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");

                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_GAS)) {
                saveData();

                Log.d(TAG, "Purchase is item. Starting use.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }

        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Consumption successful. Provisioning.");
               // mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
                saveData();
                //alert("You filled 1/4 tank. Your tank is now " + String.valueOf(mTank) + "/4 full!");
            }
            else {
                complain("Error while consuming: " + result);
            }

            Log.d(TAG, "Update UI");
            Log.d(TAG, "End consumption flow.");
        }
    };



    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }



    // Enables or disables the "please wait" screen.


    void complain(String message) {
        Log.e(TAG, "**** Error: " + message);
        alert("Error: ");
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    void saveData() {

        /*
         * WARNING: on a real application, we recommend you save data in a secure way to
         * prevent tampering. For simplicity in this sample, we simply store the data using a
         * SharedPreferences.
         */

        SharedPreferences.Editor spe = getPreferences(MODE_PRIVATE).edit();
        spe.putBoolean("IsPurchase", true);
        spe.commit();
    }


    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
    public Bitmap writeTextOnImage(Context mContext, String mText) {
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            /// Here you need to give your default image
            Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.newmineslogo);
            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // Change Text Size & Color based on your requirement
            paint.setColor(Color.WHITE);
            paint.setTextSize((int) (21 * scale));
            paint.setShadowLayer(0, 0, 0, Color.LTGRAY);
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            // Change position based on your requirement
            int x = 20;
            int y = 20;
            canvas.drawText(mText, x * scale, y * scale, paint);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v){

        ConvertTextToSpeech();

    }

    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        text = et.getText().toString();
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ttsGreater21(text);
            } else {
                ttsUnder20(text);
            }
           // tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
           // tts.speak(text+"is saved", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reader, menu);
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

    public void home(View view) {
        onPause();
        Intent intent = new Intent(this, MenuslideActivity.class);
        startActivity(intent);
    }

    public void back(View view) {
        onPause();
        onBackPressed();
    }




    public void voice_press(final View view)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //prefs.edit().putBoolean("IsPurchase", true).commit();
        boolean Ispurchase = prefs.getBoolean("IsPurchase", false);


//        if (Ispurchase)
//        {
//            tts=new TextToSpeech(ReaderActivity.this, new TextToSpeech.OnInitListener() {
//
//                @Override
//                public void onInit(int status) {
//                    // TODO Auto-generated method stub
//                    if(status == TextToSpeech.SUCCESS){
//                        int result=tts.setLanguage(Locale.US);
//                        if(result==TextToSpeech.LANG_MISSING_DATA ||
//                                result==TextToSpeech.LANG_NOT_SUPPORTED){
//                            Log.e("error", "This Language is not supported");
//                        }
//                        else{
//                            ConvertTextToSpeech();
//                        }
//                    }
//                    else
//                        Log.e("error", "Initialization Failed!");
//                }
//            });
//
//
//
//
//        } else {
//
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Do you want to buy all feature for Rs 180.00")
//                    .setCancelable(false)
//                    .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//                            onBuyGasButtonClicked(view);
//
//
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thank you for using MAGMA");
        builder.setMessage("We will provide all the features (like OCR,Offline,Search) in few days.")
                .setCancelable(false)
                .setNegativeButton("Thank You !", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();




    }


    public void pre_press(View view) {
    }

    public void offline_press(final View view)
    {

        onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean Ispurchase = prefs.getBoolean("IsPurchase", false);
//        if (Ispurchase)
//        {
//
//            MySQLiteOpenHelper db = new MySQLiteOpenHelper(this);
//            String myUser = headerText.toString();
//            String storedUser = db.Exists(myUser);
//
//
////If Username exist
//            if (myUser.equals(storedUser))
//            {
//
//
//                AlertDialog alertDialog = new AlertDialog.Builder(
//                        ReaderActivity.this).create();
//
//                // Setting Dialog Title
//                alertDialog.setTitle("Alert");
//
//                // Setting Dialog Message
//                alertDialog.setMessage("Already saved");
//
//                // Setting Icon to Dialog
//
//
//                // Setting OK Button
//                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Write your code here to execute after dialog closed
//
//                    }
//                });
//
//                // Showing Alert Message
//                alertDialog.show();
//            }
//            else
//            {
//                String str = et.getText().toString();
//
//                /**
//                 * CRUD Operations
//                 * */
//                // Inserting Contacts
//                Log.d("Insert: ", "Inserting ..");
//                boolean pdfadded = db.addUser(new User(headerText.toString(), str));
//                if(pdfadded==true)
//                {
//
//                    AlertDialog alertDialog = new AlertDialog.Builder(
//                            ReaderActivity.this).create();
//
//                    // Setting Dialog Title
//                    alertDialog.setTitle("ADDED");
//
//                    // Setting Dialog Message
//                    alertDialog.setMessage("Section saved succesfully");
//
//                    // Setting Icon to Dialog
//
//
//                    // Setting OK Button
//                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Write your code here to execute after dialog closed
//
//                        }
//                    });
//
//                    // Showing Alert Message
//                    alertDialog.show();
//                }
//                else
//                {
//                    AlertDialog alertDialog = new AlertDialog.Builder(
//                            ReaderActivity.this).create();
//
//                    // Setting Dialog Title
//                    alertDialog.setTitle("");
//
//                    // Setting Dialog Message
//                    alertDialog.setMessage("Please Try Again");
//
//                    // Setting Icon to Dialog
//
//
//                    // Setting OK Button
//                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Write your code here to execute after dialog closed
//
//                        }
//                    });
//
//                    // Showing Alert Message
//                    alertDialog.show();
//                }
//            }
//
//
//
//
//
//        } else {
//
//
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Do you want to buy all feature for Rs 180.00")
//                    .setCancelable(false)
//                    .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//
//                            onBuyGasButtonClicked(view);
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//
//
//        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thank you for using MAGMA");
        builder.setMessage("We will provide all the features (like OCR,Offline,Search) in few days.")
                .setCancelable(false)
                .setNegativeButton("Thank You !", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }
    public static String saveBitmapToFile(
            Bitmap bitmap, String path, int quality) {
        File imageFile = new File(path);
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("BitmapToTempFile", "Error writing bitmap", e);
        }
        return imageFile.getAbsolutePath();
    }

    public void share_press(View view)
    {

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
       // whatsappIntent.setPackage("com.whatsapp");
        String shareString = headerText.getText().toString() +  System.getProperty("line.separator") + et.getText().toString() +  System.getProperty("line.separator")  +  "https://play.google.com/store/apps/details?id=com.magma.minemagma" +  System.getProperty("line.separator")  + "https://itunes.apple.com/us/app/magma-the-miners-anatomy/id1161096559?ls=1&mt=8";
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareString);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            ;
        }

    }

    public void next_press(View view) {
    }
}
