package com.totyrora.fishcalc4;



import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


// FragmentActivity replaced by AppCompatActivity
public class MainActivity extends AppCompatActivity {

    private boolean imperialUnit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPreference();  // Load user preferences
        setupToolBar();     // Toolbar as ActionBar
        setupTabLayout();   // Swipe Tabs

    }

    private void setupPreference () {
        // Get app preferences
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        imperialUnit = prefs.getBoolean("imperial_unit",false);
    };

    private void setupToolBar () {
        // Use Toolbar as the ActionBar

        // Assign Toolbar as ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Use logos ActionBar with actions
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setLogo(R.mipmap.ic_char_white);
        ab.setDisplayUseLogoEnabled(true);

        // TODO Settings home
        // ab.setDisplayHomeAsUpEnabled(true);

        // Action Settings Menu is inflated by onCreateOptionsMenu

    };

    private void setupTabLayout () {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FishFragmentPageAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Get tab titles from String
        String tabTitles[] = new String [] {getResources().getString(R.string.tab1),
                getResources().getString(R.string.tab2)};

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(tabTitles[i]);
        }

    };

    @Override
    protected void onResume(){
        super.onResume();

        setupPreference(); // Load user preferences

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //call settings activity
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
