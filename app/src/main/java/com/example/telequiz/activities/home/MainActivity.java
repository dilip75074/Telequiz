package com.example.telequiz.activities.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.telequiz.R;
import com.example.telequiz.activities.account.ChangePasswordActivity;
import com.example.telequiz.activities.account.LoginActivity;
import com.example.telequiz.activities.appNotification.AppNotificationActivity;
import com.example.telequiz.activities.creatorStudio.DashboardActivity;
import com.example.telequiz.activities.home.fragments.MainPagerAdapter;
import com.example.telequiz.activities.quiz.PlayQuizActivity;
import com.example.telequiz.activities.sdkPermission.SdkPermissionManagerActivity;
import com.example.telequiz.services.AppUpdateChecker;
import com.example.telequiz.services.OverflowMenuManager;
import com.example.telequiz.services.SessionManager;
import com.example.telequiz.services.utilities.Message;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    SessionManager session;
    Context context;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    TextView textNotificationCount;
    int mNotificationCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e)
            {
                handleUncaughtException (thread, e);
            }
        });

        initAllComponents();
        session.setEssentialComponentsBasedOnSession(context, navigationView);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_notification);

        View actionView = menuItem.getActionView();
        textNotificationCount = (TextView) actionView.findViewById(R.id.notification_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        OverflowMenuManager overflowMenu = new OverflowMenuManager(menu);

        overflowMenu.showGroup(R.id.main_activity_menu_group);
        overflowMenu.showItem(R.id.app_bar_search);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings: {
                Intent intent = new Intent(context, SdkPermissionManagerActivity.class);
                startActivity(intent);
                return true;
            }

            case R.id.action_notification: {
                // Do something
                Intent intent = new Intent(context, AppNotificationActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            return;  //do nothing
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_play_quiz_logged_in) {
            Intent intent = new Intent(context, PlayQuizActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_creator_studio_looged_in) {
            Intent intent = new Intent(context, DashboardActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_password_logged_in) {
            Intent intent = new Intent(context, ChangePasswordActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_user_logout_logged_in) {
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
        Message.message(context, "Fatal Error found !");

        //System.exit(1); // kill off the crashed app
    }

    public void goForLogin(View view) {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void setupBadge() {

        if (textNotificationCount != null) {
            if (mNotificationCount == 0) {
                if (textNotificationCount.getVisibility() != View.GONE) {
                    textNotificationCount.setVisibility(View.GONE);
                }
            } else {
                textNotificationCount.setText(String.valueOf(Math.min(mNotificationCount, 99)));
                if (textNotificationCount.getVisibility() != View.VISIBLE) {
                    textNotificationCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void initAllComponents() {
        context = this;
        session = new SessionManager(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.mainTabLayout); // get the reference of TabLayout
        navigationView = findViewById(R.id.nav_view);
        new AppUpdateChecker(this).notifyUserIfAnUpdate();
    }
}
