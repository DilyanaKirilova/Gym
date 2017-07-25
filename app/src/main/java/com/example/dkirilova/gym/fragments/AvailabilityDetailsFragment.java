package com.example.dkirilova.gym.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;

import model.gyms.Availability;
import model.gyms.Gym;
import model.validators.Validator;

public class AvailabilityDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_availability_details, container, false);

        final EditText etDayName = (EditText) root.findViewById(R.id.etADDayName);
        final EditText etStartTime = (EditText) root.findViewById(R.id.etADStartTime);
        final EditText etDuration = (EditText) root.findViewById(R.id.etADDuration);
        Button btnSave = (Button) root.findViewById(R.id.btnADSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dayName = etDayName.getText().toString().trim();
                int startTime;
                int duration;

                if (!etStartTime.getText().toString().trim().isEmpty()) {
                    startTime = Integer.valueOf(etStartTime.getText().toString());
                } else {
                    etStartTime.setError("..");
                    etStartTime.requestFocus();
                    return;
                }
                if (!etDuration.getText().toString().trim().isEmpty()) {
                    duration = Integer.valueOf(etDuration.getText().toString());
                } else {
                    etDuration.setError("..");
                    etDuration.requestFocus();
                    return;
                }

                boolean isValidDayName = false;
                if (Validator.isValidString(dayName)) {

                    dayName = dayName.toUpperCase();
                    for (Availability.DayOfWeek day : Availability.DayOfWeek.values()) {
                        if (day.toString().equals(dayName)) {
                            dayName = day.toString();
                            isValidDayName = true;
                            break;
                        }
                    }

                    if (!isValidDayName) {
                        etDayName.setError("..");
                        etDayName.requestFocus();
                        return;
                    }
                }


                Availability availability = new Availability(startTime, duration, dayName);

                Gym gym = new Gym();
                if (getArguments() != null) {
                    if (getArguments().getSerializable("gym") != null) {
                        gym = (Gym) getArguments().getSerializable("gym");
                    }
                }
                if (gym != null) {
                    gym.addAvailability(availability);
                }
                ((MainActivity) getActivity()).openFragment(new GymDetailsFragment(), gym, "gym", false, R.menu.gym_details_menu);

            }
        });

        return root;
    }
}
