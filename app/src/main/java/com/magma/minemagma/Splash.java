package com.magma.minemagma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Splash extends AppCompatActivity {

    private SharedPreferences mAppPreferences;
    private SharedPreferences.Editor mEditor;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean Islogin = saved_values.getBoolean("Islogin", false);
        if (Islogin) {

            Intent intent = new Intent(this, MenuslideActivity.class);
            startActivity(intent);
            //finish();

        } else {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            //finish();

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
}
