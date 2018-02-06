package com.magma.minemagma;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by apple on 9/22/16.
 */



public class IBMAdapter extends BaseAdapter {

    Context c2;
    String[] ibm_name;
    LayoutInflater inflater2;

    public IBMAdapter(Context c2, String[] ibm_name) {
        this.c2 = c2;
        this.ibm_name = ibm_name;


        inflater2 = LayoutInflater.from(c2);
    }

    public void setIbm_name(String[] ibm_name) {
        this.ibm_name = ibm_name;
    }

    @Override
    public int getCount() {

        return ibm_name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        TextView nameText;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;
        if(convertView == null)
        {

            v = inflater2.inflate(R.layout.section_item_layout, parent, false);
            holder = new ViewHolder();
            holder.nameText = (TextView) v.findViewById(R.id.tv_sec);
            // associate the holder with the view for later lookup
            v.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) v.getTag();
        }


        if (position%2==0)
        {
            holder.nameText.setBackgroundColor(Color.rgb(252, 178, 93));
        }
        else
        {
            holder.nameText.setBackgroundColor(Color.rgb(70, 189, 98));
        }


        holder.nameText.setText(ibm_name[position]);
        return v;


    }


}
