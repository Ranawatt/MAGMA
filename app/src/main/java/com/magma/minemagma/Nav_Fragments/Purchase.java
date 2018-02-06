package com.magma.minemagma.Nav_Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.magma.minemagma.R;
import com.magma.minemagma.util.IabHelper;
import com.magma.minemagma.util.IabResult;
import com.magma.minemagma.util.Inventory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Purchase.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Purchase#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Purchase extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static final String TAG = "MAGMA";
    boolean mSubscribedToInfiniteGas = false;
    static final String SKU_GAS = "com.magma.magma.extrafeatures";
    static final int RC_REQUEST = 10001;


    // The helper object
    IabHelper mHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView uploadText;
    private Button buyButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Purchase.
     */
    // TODO: Rename and change types and number of parameters
    public static Purchase newInstance(String param1, String param2) {
        Purchase fragment = new Purchase();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Purchase() {
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Purchase More");

        final View view;

        getActivity().setTitle("Home");

        int size = getResources().getConfiguration().screenLayout;

        if((size & Configuration.SCREENLAYOUT_SIZE_LARGE) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            view = inflater.inflate(R.layout.fragment_purchase, container, false);
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_purchase, container, false);
        }

        // Inflate the layout for this fragment


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //prefs.edit().putBoolean("IsPurchase", true).commit();
        final boolean Ispurchase = prefs.getBoolean("IsPurchase", false);

        buyButton=(Button) view.findViewById(R.id.imageButton);
        buyButton.setVisibility(View.VISIBLE);

//        if (Ispurchase) {
//
//            uploadText=(TextView) view.findViewById(R.id.upload);
//            uploadText.setVisibility(View.VISIBLE);
//            buyButton=(Button) view.findViewById(R.id.imageButton);
//            buyButton.setVisibility(View.GONE);
//
//        }
//        else {
//
//            uploadText = (TextView) view.findViewById(R.id.upload);
//            uploadText.setVisibility(View.GONE);
//
//            buyButton = (Button) view.findViewById(R.id.imageButton);
//            buyButton.setVisibility(View.VISIBLE);
//        }

        buyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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



//                    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtA+8HUeE6Hx1blyJW5eHKdvkDZ/DhJ+udbvqfDKG90UvlOFY/+MuZ0QIoNaHdNOUQGP61cq2L16r85KS0F+pkLVXTQ4Y0qrOZpaU8c2jXbyz8iIL41c+lNr6C2SAZ67akbpU6Nkj8MxiG3oxN2hxjS22hGBPbYNaWxb5DPkqUmRYspKW3Mq/Lg4cRjYr/DWm280ru4OuwPP7MjeJ1POMFHEf6PdHtXNhOeCsUPWczQKAIheaF28LHBmxT34zzVN//Up5kkM0g0ZdPKDuCRTFMRb2XoLlVHaJ2Y7ePo4ITCT6hLV6ZGIFvUoqBTC/N8fcba+FdLJbqxTX0WU3hV2b/wIDAQAB";
//
//
//                    // Create the helper, passing it our context and the public key to verify signatures with
//                    Log.d(TAG, "Creating IAB helper.");
//                    mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);
//
//                    // enable debug logging (for a production application, you should set this to false).
//                    mHelper.enableDebugLogging(true);
//
//                    // Start setup. This is asynchronous and the specified listener
//                    // will be called once setup completes.
//                    Log.d(TAG, "Starting setup.");
//                    mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//                        public void onIabSetupFinished(IabResult result) {
//                            Log.d(TAG, "Setup finished.");
//
//                            if (!result.isSuccess()) {
//                                // Oh noes, there was a problem.
//                                complain("Problem setting up in-app billing: " + result);
//                                return;
//                            }
//
//                            // Have we been disposed of in the meantime? If so, quit.
//                            if (mHelper == null) return;
//
//                            // IAB is fully set up. Now, let's get an inventory of stuff we own.
//                            Log.d(TAG, "Setup successful. Querying inventory.");
//                            mHelper.queryInventoryAsync(mGotInventoryListener);
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage("Do you want to buy all feature for Rs 180.00")
//                                    .setCancelable(false)
//                                    .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//
//
//                                            onBuyGasButtonClicked(getView());
//                                        }
//                                    })
//                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                            AlertDialog alert = builder.create();
//                            alert.show();
//
//                        }
//
//
//                    });
                }

        });




        return view;

    }
    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");


            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            com.magma.minemagma.util.Purchase gasPurchase = inventory.getPurchase(SKU_GAS);
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
        Log.d(TAG, "Buy gas button clicked.");

        if (mSubscribedToInfiniteGas) {
            //complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";

        mHelper.launchPurchaseFlow(getActivity(), SKU_GAS, RC_REQUEST,
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    boolean verifyDeveloperPayload(com.magma.minemagma.util.Purchase p) {
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
        public void onIabPurchaseFinished(IabResult result, com.magma.minemagma.util.Purchase purchase) {
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

                Log.d(TAG, "Purchase is item. Starting use.");
                saveData();
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }

        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(com.magma.minemagma.util.Purchase purchase, IabResult result) {
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
        AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
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

        SharedPreferences pref = getActivity().getPreferences(0);
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean("IsPurchase", true);
        buyButton.setVisibility(View.GONE);
        uploadText.setVisibility(View.VISIBLE);
        edt.commit();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

}
