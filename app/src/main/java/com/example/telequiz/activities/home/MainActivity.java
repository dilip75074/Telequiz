package com.example.telequiz.activities.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.telequiz.activities.account.ChangePasswordActivity;
import com.example.telequiz.activities.account.LoginActivity;
import com.example.telequiz.activities.home.fragments.MainPagerAdapter;
import com.example.telequiz.R;
import com.example.telequiz.services.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer;
    NavigationView navigationView;
    Menu menu;
    SessionManager session;
    Context context;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private HashMap<String, String> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        context = getApplicationContext();

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e)
            {
                handleUncaughtException (thread, e);
            }
        });

        // Session class instance
        session = new SessionManager(context);
        userData = session.getUserDetails();

        if(session.isLoggedIn()) {
            Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show();
//            navHeaderLoggedInUserName.setText(userData.get(SessionManager.KEY_NAME));
//            navHeaderLoggedInUserEmail.setText(userData.get(SessionManager.KEY_EMAIL));
        }
        else {
            Toast.makeText(context, "Not Logged In", Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.mainTabLayout); // get the reference of TabLayout

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainPagerAdapter adapter = new MainPagerAdapter( getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        // Session class instance
        session = new SessionManager(context);

        if(session.isLoggedIn()) {
            Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Not Logged In", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            return;  //do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

        }  else if (id == R.id.nav_change_password) {
            Intent intent = new Intent(context, ChangePasswordActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_user_logout) {
            // User logout action

            new AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure want to Log out ?")
                    .setIcon(R.drawable.icon_warning)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            session.logoutUser();

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void handleUncaughtException (Thread thread, Throwable e)
    {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        Toast.makeText(this, "Fatal Error found !", Toast.LENGTH_LONG).show();
        System.exit(1); // kill off the crashed app
    }

    public void goForLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        drawer.closeDrawer(GravityCompat.START);
    }
}
