package com.example.dkirilova.gym.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.fragments.AvailabilityDetailsFragment;
import com.example.dkirilova.gym.fragments.ExerciseDetailsFragment;
import com.example.dkirilova.gym.fragments.ExerciseFragment;
import com.example.dkirilova.gym.fragments.GymDetailsFragment;
import com.example.dkirilova.gym.fragments.GymFragment;
import com.example.dkirilova.gym.fragments.GMapFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.Serializable;

import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;

import static com.example.dkirilova.gym.fragments.ExerciseFragment.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private GymFragment.IGymController iGymController;
    private GMapFragment.IMapController iMapController;
    private GymDetailsFragment.IGymDetailsController iGymDetailsController;
    private IExerciseController iExerciseController;

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
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, 123);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
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
            } else if(R.id.action_directions == id){
                iMapController.openGoogleMapsApp();
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
        }  else if (id == R.id.nav_map) {
            openMapFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openExerciseFragment() {
        configureToolbar(true, View.NO_ID);
        openFragment(new ExerciseFragment(), null, null);
    }

    public void openExerciseDetailsFragment(Gym gym) {
        configureToolbar(false, View.NO_ID);
        openFragment(new ExerciseDetailsFragment(), gym, "gym");
    }

    public void openGymDetailsFragment(Gym gym) {
        configureToolbar(false, R.menu.gym_details_menu);
        openFragment(new GymDetailsFragment(), gym, "gym");
    }

    public void openExerciseDetailsFragment(Exercise exercise) {
        configureToolbar(false, R.menu.gym_details_menu);
        openFragment(new ExerciseDetailsFragment(), exercise, "exercise");
    }

    public void openAvailabilitiesDetailsFragment(Gym gym) {
        configureToolbar(false, View.NO_ID);
        openFragment(new AvailabilityDetailsFragment(), gym, "gym");
    }

    public void openGymFragment() {
        configureToolbar(true, R.menu.gym_list_menu);
        CheckBox checkBox = (CheckBox) toolbar.getMenu().findItem(R.id.action_favourite).getActionView();
        checkBox.setButtonDrawable(R.drawable.ic_favorite_border_black_24dp);
        setFavouriteCheckState();
        openFragment(new GymFragment(), null, null);
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


    private void configureToolbar(final boolean isMainScreen, int toolbarMenu) {
        if(toolbarMenu == View.NO_ID){
            toolbar.getMenu().clear();
            return;
        }
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

    public void openMapFragment(){
        configureToolbar(true, R.menu.map_menu);
        openFragment(new GMapFragment(), null, null);
    }

    public void openFragment(Fragment fragment, Serializable serializable, String str){

        if(serializable != null && str != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(str, serializable);
            fragment.setArguments(bundle);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment).commit();
    }

    public void setIGymController(GymFragment.IGymController iGymController) {
        this.iGymController = iGymController;
    }

    public void setIMapController(GMapFragment.IMapController iMapController) {
        this.iMapController = iMapController;
    }
    public void setIGymDetailsController(GymDetailsFragment.IGymDetailsController iGymDetailsController) {
        this.iGymDetailsController = iGymDetailsController;
    }

    public void setIExerciseController(IExerciseController iExerciseController) {
        this.iExerciseController = iExerciseController;
    }

    public void editOrDelete(final Gym gym, final Exercise exercise) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or delete");
        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(gym != null) {
                    openGymDetailsFragment(gym);
                }else if(exercise != null){
                    openExerciseDetailsFragment(exercise);
                }
            }
        });
        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(gym != null) {
                    FitnessManager.getInstance().delete(gym);
                    openGymFragment();
                }else if(exercise != null){
                    FitnessManager.getInstance().delete(exercise);
                    openExerciseFragment();
                }
            }
        });
        builder.show();
    }
}
