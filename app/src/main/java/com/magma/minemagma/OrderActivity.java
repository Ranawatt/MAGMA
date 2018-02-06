package com.magma.minemagma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class OrderActivity extends AppCompatActivity
{
    GridView grid;
    String[] web =
            {
    "WP (C) No. 202 of 1995","WP(C) No.562 of 2009","WP (C) No. 435 of 2012","WP (C) No. 114 of 2014",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrderAdapter adapter = new OrderAdapter(OrderActivity.this, web);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(OrderActivity.this, OrderSecActivity.class);
                intent.putExtra("sectionIndex", position);
                startActivity(intent);

            }
        });
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

//    public void next(View view)
//    {
//        Intent intent = new Intent(this, OrderSecActivity.class);
//        startActivity(intent);
//    }

    public void back(View view) {
        onBackPressed();
    }
}
