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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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

    public interface IExerciseController {
        void showExercises();
    }

    public interface IGymController {
        void showFavouritesGyms();

        void showAllGyms();
    }

    public interface IGymDetailsController {
        void editGym();
    }

    // fragments
    private GymFragment gymFragment = new GymFragment();
    private GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
    private ExerciseFragment exerciseFragment = new ExerciseFragment();
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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(defaultMenuListener);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        openGymFragment();
    }

    private final Toolbar.OnMenuItemClickListener defaultMenuListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();

            if (R.id.action_add == id) {
                openGymDetailsFragment(new Gym());
                return true;
            } else if (R.id.action_edit == id) {
                iGymDetailsController.editGym();
                return true;
            }

            return false;
        }
    };

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
        configureToolbar(true, View.NO_ID);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, new ExerciseFragment()).commit();
    }

    public void openExerciseDetailsFragment(Gym gym) {

        configureToolbar(false, View.NO_ID);
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
        exerciseDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
    }

    public void openGymDetailsFragment(Gym gym) {

        configureToolbar(false, R.menu.gym_details_menu);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        gymDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, gymDetailsFragment).commit();
    }

    public void openExerciseDetailsFragment(Exercise exercise) {

        configureToolbar(false, R.menu.gym_details_menu);
        // fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
        exerciseDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
    }

    public void openAvailabilitiesDetailsFragment(Gym gym) {
        //change lock mode
        configureToolbar(false, View.NO_ID);
        //  fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        AvailabilityDetailsFragment availabilityDetailsFragment = new AvailabilityDetailsFragment();
        availabilityDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, availabilityDetailsFragment).commit();
    }

    public void openGymFragment() {
        //  fragment transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, new GymFragment()).commit();
        //change lock mode
        configureToolbar(true, R.menu.gym_list_menu);
        setFavouriteCheckState();
    }

    private void setFavouriteCheckState() {
        final CheckBox checkBox = (CheckBox) toolbar.getMenu().findItem(R.id.action_favourite).getActionView();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkBox.setButtonDrawable(R.drawable.ic_favorite_black_24dp);
                    iGymController.showFavouritesGyms();
                } else {
                    checkBox.setButtonDrawable(R.drawable.ic_favorite_border_black_24dp);
                    iGymController.showAllGyms();
                }
            }
        });
    }

    public void openEditOrDeleteFragment(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        EditOrDeleteFragment editOrDeleteFragment = new EditOrDeleteFragment();
        editOrDeleteFragment.setArguments(bundle);
        editOrDeleteFragment.show(getSupportFragmentManager(), "fragment");
    }

    public void openEditOrDeleteFragment(Gym gym) {
        //  fragment transaction
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        EditOrDeleteFragment editOrDeleteFragment = new EditOrDeleteFragment();
        editOrDeleteFragment.setArguments(bundle);
        editOrDeleteFragment.show(getSupportFragmentManager(), "fragment");
    }

    private void configureToolbar(final boolean isMainScreen, int toolbarMenu) {
        drawer.setDrawerLockMode(isMainScreen ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(isMainScreen ? R.drawable.ic_menu_black_24dp : R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(toolbarMenu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMainScreen) {
                    drawer.openDrawer(Gravity.START);
                } else {
                    openGymFragment();
                }
            }
        });
    }

}
