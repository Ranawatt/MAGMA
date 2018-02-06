package com.magma.minemagma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.magma.minemagma.Nav_Fragments.AboutUs;
import com.magma.minemagma.Nav_Fragments.ContactUs;
import com.magma.minemagma.Nav_Fragments.Information;
import com.magma.minemagma.Nav_Fragments.OflDoc;
import com.magma.minemagma.Nav_Fragments.PromAdv;
import com.magma.minemagma.Nav_Fragments.Purchase;
import com.magma.minemagma.Nav_Fragments.RateItDialogFragment;
import com.magma.minemagma.Nav_Fragments.Search;

public class MenuslideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AboutUs.OnFragmentInteractionListener,
        ContactUs.OnFragmentInteractionListener,Information.OnFragmentInteractionListener,OflDoc.OnFragmentInteractionListener,PromAdv.OnFragmentInteractionListener,Purchase.OnFragmentInteractionListener,
        Search.OnFragmentInteractionListener {

 //   public static String PREFS_NAME;
    private TextView profilename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuslide);

        Bundle extras = getIntent().getExtras();
       // String profiletitle = extras.getString("loginUser");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Information");

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = Information.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        RateItDialogFragment.show(this, getFragmentManager());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        profilename = (TextView)hView.findViewById(R.id.profilename);
       // profilename.setText(profiletitle);

        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String Userlogin = saved_values.getString("loginUSERS","Profile Name");
        profilename.setText(Userlogin);

        navigationView.setNavigationItemSelectedListener(this);




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menuslide, menu);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_contact) {
            fragmentClass = ContactUs.class;
        } else if (id == R.id.nav_about) {
            fragmentClass = AboutUs.class;
        }
        else if (id == R.id.nav_home) {
            fragmentClass = Information.class;
        }
        else if (id == R.id.nav_promadv) {
            fragmentClass = PromAdv.class;
        }
        else if (id == R.id.nav_search) {
            fragmentClass = Search.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}




