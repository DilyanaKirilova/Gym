package com.example.dkirilova.gym.activities;

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
import com.example.dkirilova.gym.fragments.GymDetailsFragment;
import com.example.dkirilova.gym.fragments.GymFragment;
import com.example.dkirilova.gym.fragments.ExerciseFragment;

import static android.support.design.widget.NavigationView.*;


public class MainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener {

    // fragments
    private GymFragment gymFragment = new GymFragment();
    private ExerciseFragment exerciseFragment = new ExerciseFragment();
    private GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();

    // interface
    IGymController iGymController = (IGymController) gymFragment;
    IExerciseController iExerciseController = (IExerciseController) exerciseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.replace(R.id.fragmentContainer, gymFragment).commit();

        final CheckBox chbFavourites = (CheckBox) findViewById(R.id.chbFavourite);
        final ImageButton ibBack = (ImageButton) findViewById(R.id.ibBack);
        final ImageButton ibEdit = (ImageButton) findViewById(R.id.ibEdit);
        final ImageButton ibAdd = (ImageButton) findViewById(R.id.ibAdd);

        // onClickListener toolbar icon (add)
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGymController.addGym();
            }
        });


        // onClickListener toolbar icon (edit)
        ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // todo
            }
        });

        // onClickListener toolbar icon (back)
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // onClickListener toolbar icon (favourites)
        chbFavourites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    chbFavourites.setButtonDrawable(R.drawable.ic_favorite_black_24dp);
                    iGymController.showFavouritesGyms();
                } else {
                    chbFavourites.setButtonDrawable(R.drawable.ic_favorite_border_black_24dp);
                    iGymController.showAllGyms();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);


        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item click event
                        return true;
                    }
                });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        int id = item.getItemId();

        if (id == R.id.nav_gyms) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, gymFragment).commit();
        } else if (id == R.id.nav_exercises) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, exerciseFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public interface IExerciseController {

        void showExercises();
    }

    public interface IGymController {
        void showFavouritesGyms();
        void showAllGyms();
        void addGym();
    }
}
