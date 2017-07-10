package com.example.dkirilova.gym.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.fragments.MainFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CheckBox chbFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton ibAdd = (ImageButton) findViewById(R.id.ibAdd);
        chbFavourite = (CheckBox) findViewById(R.id.chbFavourite);

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("replace_fragment", "add_gym");
                startActivity(intent);

            }
        });

        chbFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                MainFragment mainFragment = new MainFragment();
                Bundle bundle = new Bundle();
                if (isChecked) {
                    chbFavourite.setButtonDrawable(R.mipmap.ic_favorite_black_24dp);
                    bundle.putString("recycler_view", "favourites");
                } else {
                    chbFavourite.setButtonDrawable(R.mipmap.ic_favorite_border_black_24dp);
                }

                FragmentTransaction f = getSupportFragmentManager().beginTransaction();
                mainFragment.setArguments(bundle);
                f.replace(R.id.fragmentContainer, mainFragment).commit();
            }
        });


       //replace with fragment with fitness list

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new MainFragment()).commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();

        if (id == R.id.nav_gyms) {
            bundle.putString("replace_fragment", "gym");
        } else if (id == R.id.nav_exercises) {
            bundle.putString("replace_fragment", "exercises");
        }

        mainFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer, mainFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
