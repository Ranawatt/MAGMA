package com.magma.minemagma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    private TextView mTextview;
    private TextView mTitleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);


        Intent i = getIntent();
        pdfParse pdfText = (pdfParse)i.getSerializableExtra("sampleObject");
        mTextview = (TextView)findViewById(R.id.textView14);
        mTitleview = (TextView)findViewById(R.id.textView15);

        mTextview.setMovementMethod(new ScrollingMovementMethod());

        mTextview.setText(pdfText.gettext());
        mTitleview.setText(pdfText.getname());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void back(View view) {
        onBackPressed();
    }
}
