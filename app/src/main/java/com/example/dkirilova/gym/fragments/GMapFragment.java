package com.example.dkirilova.gym.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dkirilova.gym.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import model.gyms.Gym;
import model.singleton.FitnessManager;

import static com.example.dkirilova.gym.R.id;
import static com.example.dkirilova.gym.R.layout;

public class GMapFragment extends Fragment implements OnMapReadyCallback {


    private double latitude = 0;
    private double longitude = 0;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(layout.fragment_gmap, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                // todo
            }

            @Override
            public void onProviderDisabled(String provider) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Location services are off");
                builder.setMessage("Please turn on location services to continue");
                builder.setPositiveButton("Turn on location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(getActivity() instanceof MainActivity){
                            ((MainActivity)getActivity()).openGymFragment();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }else {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }

        if (location == null) {
            Toast.makeText(getActivity(), "Location not found", Toast.LENGTH_SHORT).show();
        } else {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        LatLng marker = new LatLng(latitude, longitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15));
        googleMap.addMarker(new MarkerOptions().title("Your Location").position(marker));

        for(Gym gym : FitnessManager.getInstance().getAllGyms()){
            LatLng m = new LatLng(gym.getLatitude(), gym.getLongitude());
            googleMap.addMarker(new MarkerOptions().title("Name: " + gym.getName()).position(m));
        }
    }
}
