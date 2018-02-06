package com.magma.minemagma.Nav_Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.magma.minemagma.CustomGridAdapter;
import com.magma.minemagma.Grid3Adapter;
import com.magma.minemagma.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PromAdv.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PromAdv#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromAdv extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GridView gridView;
    private de.hdodenhof.circleimageview.CircleImageView imageView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
//    private GridView grid;
//    private CustomGridAdapter adapter1;
//    private Grid3Adapter adapter2;
//    String[] circ_tv = {"Sabyasachi","Rakesh","Jayavel","Manjunath","Jyoti Prakash"};
//    Integer [] circ = {R.drawable.sabyasachi, R.drawable.rakeshmedited, R.drawable.jayavel, R.drawable.editedmanju, R.drawable.editedjp};
//    String[] circ_tv2 = {"P.Venugopal", "Hanumantraya", "Kulkarni", "B.P.Pandey","Shreenivasa","Dhananjaya","Ramani","Shekhar","Manjunath","Nayaz"};
//    int[] circ2 = {R.drawable.editedvenugopal, R.drawable.rayappaedited, R.drawable.kulkarni, R.drawable.editedpanday, R.drawable.editedshreenivasa, R.drawable.dhananjaya, R.drawable.kramaniedited, R.drawable.shekharedited, R.drawable.editedmanjunath, R.drawable.editednayaz};


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PromAdv.
     */
    // TODO: Rename and change types and number of parameters
    public static PromAdv newInstance(String param1, String param2) {
        PromAdv fragment = new PromAdv();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PromAdv() {
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Financer & Adviser");

        View view = inflater.inflate(R.layout.fragment_prom_adv, container,false);

        GridView gridView = (GridView) view.findViewById(R.id.grid2);
        GridView gridView2 = (GridView) view.findViewById(R.id.grid3);
        gridView.setAdapter(new CustomGridAdapter(view.getContext()));
        gridView2.setAdapter(new Grid3Adapter(view.getContext()));

        return view;

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
