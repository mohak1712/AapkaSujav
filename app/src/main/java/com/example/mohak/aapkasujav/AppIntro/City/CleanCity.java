package com.example.mohak.aapkasujav.AppIntro.City;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohak.aapkasujav.AppIntro.MaterialTab.Tab1;
import com.example.mohak.aapkasujav.AppIntro.MaterialTab.Tab2;
import com.example.mohak.aapkasujav.AppIntro.PostImage;
import com.example.mohak.aapkasujav.AppIntro.RecyclerV.Recycler;
import com.example.mohak.aapkasujav.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class CleanCity extends AppCompatActivity {

    static final int ACTIVITY_SELECT_IMAGE = 2;
    String uid2;
    Toolbar toolbar;
    ViewPager viewPager;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    TabLayout tabLayout;
    ImageView imageView;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_city);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // setting the navigation drawer for swipe
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Clean City");
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coor);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        imageView = (ImageView) findViewById(R.id.swatch);
        imageView.setImageResource(R.drawable.swachhbharat_logo);
        setupviewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        collapsingToolbarLayout.setTitle("Clean City Clean India");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.primaryColor));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isNetworkAvailable()) {

                } else {

                    snack(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private void snack(final int pos) {

        Snackbar.make(coordinatorLayout, "Unable to Connect", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()) {
                    //setupviewpager to be called for setting up the view pager again with the two fragments
                    setupviewPager(viewPager);
                    viewPager.setCurrentItem(pos);
                } else {

                    snack(pos);
                }
            }
        }).
                show();
    }


    private void setupviewPager(ViewPager viewPager) {

        MypagerAdapter adapter = new MypagerAdapter(getSupportFragmentManager());
        adapter.addFragment(Tab1.newInstance("", ""), "My Post");
        adapter.addFragment(Tab2.newInstance("", ""), "Others");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
            Log.d("CO", "co");
        } else {
            Networkalert();
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CleanCity.this, Recycler.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clean_city, menu);
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
        if (id == R.id.Comment) {
            createalert();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createalert() {

        if (isNetworkAvailable()) {

            Intent intent = new Intent(CleanCity.this, PostImage.class);
            startActivity(intent);
//
        } else {
            Networkalert();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {


        } else if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

        }
    }


    public void Networkalert() {

    }


    public class MypagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> fragmentslist = new ArrayList<>();
        ArrayList<String> titlelist = new ArrayList<>();
        int icon[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};

        public MypagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentslist.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentslist.add(fragment);
            titlelist.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titlelist.get(position);
        }

        @Override
        public int getCount() {
            return fragmentslist.size();
        }

        public android.graphics.drawable.Drawable getPageIcon(int position) {
            return ResourcesCompat.getDrawable(getResources(), icon[position], null);
        }


    }
}
