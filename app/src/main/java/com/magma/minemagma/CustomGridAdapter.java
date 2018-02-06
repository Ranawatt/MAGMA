package com.magma.minemagma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by apple on 9/13/16.
 */

public class CustomGridAdapter extends BaseAdapter {

    private Context mContext2;
    private Integer[] circ = {R.drawable.sabyasachi, R.drawable.rakeshmedited, R.drawable.jayavel,
            R.drawable.editedmanju, R.drawable.editedjp, R.drawable.sugandh};
    private String[] circ_tv = {"Sabyasachi","Rakesh","Jayavel","Manjunath","Jyoti Prakash","Sugandh"};

    public CustomGridAdapter(Context c) {
        mContext2 = c;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
//        return circ.length;
        return circ_tv.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid2;
        de.hdodenhof.circleimageview.CircleImageView imageView;
        TextView textView;
        LayoutInflater inflater = (LayoutInflater) mContext2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid2 = new View(mContext2);
            grid2 = inflater.inflate(R.layout.gridview, null);
            imageView = (de.hdodenhof.circleimageview.CircleImageView) grid2.findViewById(R.id.circ);
            textView = (TextView) grid2.findViewById(R.id.circ_tv);

            imageView.setImageResource(circ[position]);
            textView.setText(circ_tv[position]);

//        if (convertView == null) { // if it's not recycled, initialize some
//            // attributes
//            imageView = new de.hdodenhof.circleimageview.CircleImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        } else {
            grid2 = (View) convertView;
        }
        return grid2;
    }



}