package com.magma.minemagma.Nav_Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.magma.minemagma.HomeActivity;
import com.magma.minemagma.IBMCircular;
import com.magma.minemagma.LinkActivity;
import com.magma.minemagma.MOEFActivity;
import com.magma.minemagma.OrderActivity;
import com.magma.minemagma.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Information.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Information#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Information extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ActionBar actionBar;
    private WindowManager winManager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Information.
     */
    // TODO: Rename and change types and number of parameters
    public static Information newInstance(String param1, String param2) {
        Information fragment = new Information();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Information() {
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        getActivity().setTitle("Home");



        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        int size = getResources().getConfiguration().screenLayout;

        if((size & Configuration.SCREENLAYOUT_SIZE_LARGE) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            view = inflater.inflate(R.layout.fragment_information, container, false);
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_information, container, false);
        }

        LinearLayout btnNextScreen = (LinearLayout) view.findViewById(R.id.first_button);
        LinearLayout btnSecondScreen = (LinearLayout) view.findViewById(R.id.second_button);
        LinearLayout btnThirdScreen = (LinearLayout) view.findViewById(R.id.third_button);
        LinearLayout btnFiveScreen = (LinearLayout) view.findViewById(R.id.five_button);
        LinearLayout btnSixScreen = (LinearLayout) view.findViewById(R.id.six_button);

        //Listening to button event
        btnNextScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getActivity(), HomeActivity.class);
                startActivity(nextScreen);

            }
        });

        btnThirdScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getActivity(), IBMCircular.class);
                startActivity(nextScreen);

            }
        });

        btnFiveScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getActivity(), MOEFActivity.class);
                startActivity(nextScreen);

            }
        });
        btnSecondScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getActivity(), OrderActivity.class);
                startActivity(nextScreen);

            }
        });
        btnSixScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getActivity(), LinkActivity.class);
                startActivity(nextScreen);

            }
        });

        return view;
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


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
