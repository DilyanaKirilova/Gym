package com.example.dkirilova.gym.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.fragments.GymDetailsFragment;

import model.gyms.Gym;

public class DetailsActivity extends AppCompatActivity{

    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageButton ibEdit = (ImageButton) findViewById(R.id.ibEdit);
        ImageButton ibBack = (ImageButton) findViewById(R.id.ibBack);

        if (getIntent().getStringExtra("replace_fragment") != null) {

            if (getIntent().getStringExtra("replace_fragment").equals("add_gym")) {
                ibEdit.setVisibility(View.GONE);

            } else if(getIntent().getStringExtra("replace_fragment").equals("gym_details")){
                bundle = getIntent().getBundleExtra("gym");
                Gym gym = (Gym) bundle.getSerializable("gym");
                bundle.putSerializable("gym", gym);
            }

            ibEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
                    bundle.putString("edit", "gym");
                    gymDetailsFragment.setArguments(bundle);
                    FragmentTransaction f = getSupportFragmentManager().beginTransaction();
                    f.replace(R.id.fragmentContainerDetails, gymDetailsFragment).commit();
                }
            });

            GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
            gymDetailsFragment.setArguments(bundle);
            FragmentTransaction f = getSupportFragmentManager().beginTransaction();
            f.replace(R.id.fragmentContainerDetails, gymDetailsFragment).commit();

            ibBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
