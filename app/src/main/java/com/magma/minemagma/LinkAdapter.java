package com.magma.minemagma;

/**
 * Created by Jyoti on 9/20/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LinkAdapter extends BaseAdapter {
    private final LayoutInflater inflater2;
    private Context mContext;
    private final String[] web;


    public LinkAdapter(Context c,String[] web ) {
        mContext = c;

        this.web = web;


        inflater2 = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
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
//        View grid;
//        LayoutInflater inflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = inflater2.inflate(R.layout.grid_link, parent, false);
        }

        //if (convertView == null) {

            //grid = new View(mContext);
            //grid = inflater.inflate(R.layout.grid_link, null);
            TextView textView = (TextView) convertView.findViewById(R.id.webText);
//            WebView webView = (WebView) convertView.findViewById(R.id.imageWeb);
//        webView.setTag(Integer.valueOf(position));
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setDomStorageEnabled(true);
            textView.setText(web[position]);
    //    webView.loadUrl(web[position]);

//        } else {
//           // grid = (View) convertView;
//        }

        return convertView;
    }
}

