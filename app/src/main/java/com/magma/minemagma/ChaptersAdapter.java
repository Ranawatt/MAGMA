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
 * Created by apple on 9/16/16.
 */
public class ChaptersAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> chap_name;
    LayoutInflater inflater;
    Context context;

    public ChaptersAdapter(Context c, ArrayList<String> chap_name) {
        this.c = c;
        this.chap_name = chap_name;


        inflater = LayoutInflater.from(c);
    }

    public void setChap_name(ArrayList<String> chap_name) {
        this.chap_name = chap_name;
    }

    @Override
    public int getCount() {
        return chap_name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    public static int getHeight(TextView t) {
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth(t.getContext()), View.MeasureSpec.AT_MOST);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        t.measure(widthMeasureSpec, heightMeasureSpec);
//        return t.getMeasuredHeight();
//    }
//
//    public static int screenWidth(Context context)
//    {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        return display.getWidth();
//    }

    static class ViewHolder {
        TextView nameText;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(convertView == null)
        {

            v = inflater.inflate(R.layout.chapters_item_layout, parent, false);
            holder = new ViewHolder();
            holder.nameText = (TextView) v.findViewById(R.id.tv_ch);
            // associate the holder with the view for later lookup
            v.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) v.getTag();
        }


        if(position%4==0)
        {
            holder.nameText.setBackgroundColor(Color.rgb(237, 28, 36));
        }
        else if (position%3==0)
        {
            holder.nameText.setBackgroundColor(Color.rgb(75, 191, 191));
        }
        else if (position%2==0)
        {
            holder.nameText.setBackgroundColor(Color.rgb(252, 178, 93));
        }
        else
        {
            holder.nameText.setBackgroundColor(Color.rgb(70, 189, 98));
        }

        holder.nameText.setText(chap_name.get(position));
        return v;
    }
}
