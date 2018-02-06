package com.magma.minemagma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by apple on 9/15/16.
 */
public class Grid3Adapter extends BaseAdapter {

    private Context mContext3;
    String[] circ_tv2 = {"P.Venugopal", "Hanumantraya", "Kulkarni", "B.P.Pandey","Shreenivasa","Dhananjaya","Ramani","Shekhar","Manjunath","Nayaz"};

    public Grid3Adapter(Context c) {
        mContext3 = c;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return circ_tv2.length;
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

        View grid3;
        de.hdodenhof.circleimageview.CircleImageView imageView;
        TextView textView;
        LayoutInflater inflater = (LayoutInflater) mContext3.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid3 = new View(mContext3);
            grid3 = inflater.inflate(R.layout.gridview2, null);

            //imageView = (de.hdodenhof.circleimageview.CircleImageView) grid3.findViewById(R.id.circ2);
            //imageView.setImageResource(circ2[position]);

            textView = (TextView) grid3.findViewById(R.id.circ_tv2);
            textView.setText(circ_tv2[position]);

        } else {
            grid3 = (View) convertView;
        }
        return grid3;
    }
}

