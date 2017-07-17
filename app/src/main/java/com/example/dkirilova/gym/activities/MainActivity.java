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
import com.example.dkirilova.gym.dialog_fragments.EditOrDeleteFragment;
import com.example.dkirilova.gym.fragments.AvailabilityDetailsFragment;
import com.example.dkirilova.gym.fragments.ExerciseDetailsFragment;
import com.example.dkirilova.gym.fragments.GymDetailsFragment;
import com.example.dkirilova.gym.fragments.GymFragment;
import com.example.dkirilova.gym.fragments.ExerciseFragment;

import java.util.ArrayList;

import model.gyms.Exercise;
import model.gyms.Gym;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // fragments
    private GymFragment gymFragment = new GymFragment();
    private ExerciseFragment exerciseFragment = new ExerciseFragment();
    private static ImageButton ibBack;
    private static ImageButton ibEdit;
    private static ImageButton ibAdd;
    private static CheckBox chbFavourites;

    // interface
    IGymController iGymController = (IGymController) gymFragment;
    IExerciseController iExerciseController = (IExerciseController) exerciseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chbFavourites = (CheckBox) findViewById(R.id.chbFavourite);
        ibBack = (ImageButton) findViewById(R.id.ibBack);
        ibEdit = (ImageButton) findViewById(R.id.ibEdit);
        ibAdd = (ImageButton) findViewById(R.id.ibAdd);

        openGymFragment();

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
            //editGym();
            }
        });

        // onClickListener toolbar icon (back)
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGymFragment();
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
            openGymFragment();
        } else if (id == R.id.nav_exercises) {
            openExerciseFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openExerciseFragment() {
        // change visibility
        ibBack.setVisibility(View.GONE);
        ibEdit.setVisibility(View.GONE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        // fragment transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, new ExerciseFragment()).commit();
    }

    public void openGymDetailsFragment(Gym gym) {
        // change visibility
        ibBack.setVisibility(View.VISIBLE);
        ibEdit.setVisibility(View.VISIBLE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
        gymDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, gymDetailsFragment).commit();
    }

    public void openExerciseDetailsFragment(Exercise exercise) {
        // change visibility
        ibBack.setVisibility(View.VISIBLE);
        ibEdit.setVisibility(View.VISIBLE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
        exerciseDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
    }

    public void openEditOrDeleteFragment(Exercise exercise) {
        // change visibility
        ibBack.setVisibility(View.GONE);
        ibEdit.setVisibility(View.GONE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        EditOrDeleteFragment editOrDeleteFragment = new EditOrDeleteFragment();
        editOrDeleteFragment.setArguments(bundle);
        editOrDeleteFragment.show(getSupportFragmentManager(), "fragment");
    }

    public void openAvailabilitiesDetailsFragment(Gym gym) {
        // change visibility
        ibBack.setVisibility(View.GONE);
        ibEdit.setVisibility(View.GONE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        //  fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        AvailabilityDetailsFragment availabilityDetailsFragment = new AvailabilityDetailsFragment();
        availabilityDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, availabilityDetailsFragment).commit();
    }

    public void openExerciseDetailsFragment(ArrayList<Exercise> newExercises) {
        // change visibility
        ibBack.setVisibility(View.VISIBLE);
        ibEdit.setVisibility(View.VISIBLE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        //  fragment transaction
        Bundle bundle = new Bundle();
        bundle.putString("edit", "exercise");
        bundle.putSerializable("array", newExercises);
        ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
        exerciseDetailsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
    }

    public void openEditOrDeleteFragment(Gym gym) {
        // change visibility
        ibBack.setVisibility(View.GONE);
        ibEdit.setVisibility(View.GONE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        //  fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        EditOrDeleteFragment editOrDeleteFragment = new EditOrDeleteFragment();
        editOrDeleteFragment.setArguments(bundle);
        editOrDeleteFragment.show(getSupportFragmentManager(), "fragment");
    }

    public void openGymDetailsFragment(ArrayList<Exercise> exercises) {
        // change visibility
        ibBack.setVisibility(View.VISIBLE);
        ibEdit.setVisibility(View.VISIBLE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        //  fragment transaction
        GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("array", exercises);
        gymDetailsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, gymDetailsFragment).commit();
    }

    public void openGymDetailsFragment() {
        // change visibility
        ibBack.setVisibility(View.VISIBLE);
        ibEdit.setVisibility(View.VISIBLE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        //  fragment transaction
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new GymDetailsFragment()).commit();
    }

    public interface IExerciseController {
        void showExercises();
    }

    public interface IGymController {
        void showFavouritesGyms();
        void showAllGyms();
        void addGym();
    }

    public void openGymFragment(){
        // change visibility
        ibBack.setVisibility(View.GONE);
        ibEdit.setVisibility(View.GONE);
        ibAdd.setVisibility(View.VISIBLE);
        chbFavourites.setVisibility(View.VISIBLE);
        //  fragment transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, new GymFragment()).commit();
    }
}
