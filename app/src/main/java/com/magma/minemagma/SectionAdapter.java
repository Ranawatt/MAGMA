package com.magma.minemagma;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jyoti on 9/16/16.
 */
public class SectionAdapter extends BaseAdapter{


    Context c2;
    ArrayList<String> sec_name;
    LayoutInflater inflater2;


    public SectionAdapter(Context c2, ArrayList<String> sec_name) {
        this.c2 = c2;
        this.sec_name = sec_name;


        inflater2 = LayoutInflater.from(c2);
    }

    public void setSec_name(ArrayList<String> sec_name) {
        this.sec_name = sec_name;
    }

    @Override
    public int getCount() {
        return sec_name.size();
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


        holder.nameText.setText(sec_name.get(position));
        return v;

    }

}
