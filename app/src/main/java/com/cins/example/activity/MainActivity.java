package com.cins.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.cins.example.AppConstants;
import com.cins.example.fragment.CardContentFragment;
import com.cins.example.fragment.ListContentFragment;
import com.cins.example.R;
import com.cins.example.fragment.TileContentFragment;
import com.cins.example.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Activity mActivity;
    private int currentIndex;
    private int mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
    private boolean mIsAddedView;
    NavigationView navigationView;
    private boolean mIsChangeTheme;
    private DrawerLayout drawer;
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        setContentView(R.layout.activity_main);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mIsChangeTheme) {
                    mIsChangeTheme = false;
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }
            }
        });



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuNightMode = navigationView.getMenu().findItem(R.id.nav_night_mode);
        SwitchCompat dayNightSwitch = (SwitchCompat) MenuItemCompat
                .getActionView(menuNightMode);
        initNightModeSwitch();
    }

    private void initNightModeSwitch() {
        MenuItem menuNightMode = navigationView.getMenu().findItem(R.id.nav_night_mode);
        SwitchCompat dayNightSwitch = (SwitchCompat) MenuItemCompat
                .getActionView(menuNightMode);
        setCheckedState(dayNightSwitch);
        setCheckedEvent(dayNightSwitch);
    }

    private void setCheckedState(SwitchCompat dayNightSwitch) {
        boolean isNight = SharedPreferencesUtil.getBoolean(this, AppConstants.ISNIGHT, false);
        if (isNight) {
            dayNightSwitch.setChecked(true);
        } else {
            dayNightSwitch.setChecked(false);
        }
    }

    private void setCheckedEvent(SwitchCompat dayNightSwitch) {
        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                mIsChangeTheme = true;
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }
    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListContentFragment(),"List");
        adapter.addFragment(new TileContentFragment(),"Tile");
        adapter.addFragment(new CardContentFragment(),"Card");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        } else if (id==R.id.action_day_night_yes){

            //Set the local night mode to some value
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //调用 recreate(); 使设置生效
            getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            recreate();
            return true;
        } else if (id == R.id.action_day_night_no) {

            //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            //recreate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_aboutme) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this,TestActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if(id == R.id.nav_component){
            Intent intent = new Intent(this,ComponentActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_night_mode) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //夜间模式
/*
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
            case Configuration.UI_MODE_NIGHT_YES:
                mDayNightMode = AppCompatDelegate.MODE_NIGHT_YES;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
        }*/
        /*int uiMode = getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_NO) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
            mStatusTextView.setText(R.string.text_for_day_night_mode_night_no);
        } else if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_YES;
            //mStatusTextView.setText(R.string.text_for_day_night_mode_night_yes);
        } else {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
            //mStatusTextView.setText(R.string.text_for_day_night_mode_night_auto);
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putInt(AppConstants.CURRENT_INDEX,currentIndex);
        super.onSaveInstanceState(outState);
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
    public void switchContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.componentLayout, fragment).commit();
        invalidateOptionsMenu();
    }
}
