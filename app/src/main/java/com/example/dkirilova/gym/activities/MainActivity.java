package com.example.dkirilova.gym.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
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
import com.example.dkirilova.gym.fragments.ExerciseFragment;
import com.example.dkirilova.gym.fragments.GymDetailsFragment;
import com.example.dkirilova.gym.fragments.GymFragment;

import model.gyms.Exercise;
import model.gyms.Gym;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public void openExerciseDetailsFragment(Gym gym) {
        ibBack.setVisibility(View.VISIBLE);
        ibEdit.setVisibility(View.VISIBLE);
        ibAdd.setVisibility(View.GONE);
        chbFavourites.setVisibility(View.GONE);
        //change lock mode
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(null);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
        exerciseDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
    }

    public interface IExerciseController {
        void showExercises();
    }

    public interface IGymController {
        void showFavouritesGyms();
        void showAllGyms();
    }

    public interface IGymDetailsController{
        void editGym();
    }
    // fragments
    private GymFragment gymFragment = new GymFragment();
    private GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
    private ExerciseFragment exerciseFragment = new ExerciseFragment();
    private ImageButton ibBack;
    private ImageButton ibEdit;
    private ImageButton ibAdd;
    private CheckBox chbFavourites;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    // interface
    IGymController iGymController = (IGymController) gymFragment;
    IGymDetailsController iGymDetailsController = (IGymDetailsController) gymDetailsFragment;
    IExerciseController iExerciseController = (IExerciseController) exerciseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chbFavourites = (CheckBox) findViewById(R.id.chbFavourite);
        ibBack = (ImageButton) findViewById(R.id.ibBack);
        ibEdit = (ImageButton) findViewById(R.id.ibEdit);
        ibAdd = (ImageButton) findViewById(R.id.ibAdd);

        // onClickListener toolbar icon (add)
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGymDetailsFragment(new Gym());
            }
        });

        // onClickListener toolbar icon (edit)
        ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGymDetailsController.editGym();
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


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        openGymFragment();
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
        //change lock mode
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
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
        //change lock mode
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(null);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
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
        //change lock mode
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(null);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
        exerciseDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
    }

    public void openEditOrDeleteFragment(Exercise exercise) {
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
        //change lock mode
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(null);
        //  fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        AvailabilityDetailsFragment availabilityDetailsFragment = new AvailabilityDetailsFragment();
        availabilityDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, availabilityDetailsFragment).commit();
    }

    public void openEditOrDeleteFragment(Gym gym) {
        //  fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        EditOrDeleteFragment editOrDeleteFragment = new EditOrDeleteFragment();
        editOrDeleteFragment.setArguments(bundle);
        editOrDeleteFragment.show(getSupportFragmentManager(), "fragment");
    }

    public void openGymFragment(){
        // change visibility
        ibBack.setVisibility(View.GONE);
        ibEdit.setVisibility(View.GONE);
        ibAdd.setVisibility(View.VISIBLE);
        chbFavourites.setVisibility(View.VISIBLE);
        //change lock mode
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        //  fragment transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, new GymFragment()).commit();
    }
}
