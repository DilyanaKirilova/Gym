package com.example.dkirilova.gym.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import model.gyms.Gym;
import model.singleton.FitnessManager;

import static com.example.dkirilova.gym.R.id;
import static com.example.dkirilova.gym.R.layout;

public class GMapFragment extends Fragment implements OnMapReadyCallback {

    public interface IMapController {
        void openGoogleMapsApp();
    }

    private IMapController iMapController = new IMapController() {
        @Override
        public void openGoogleMapsApp() {
            if (latitudeUser == latitudeGym && longitudeUser == longitudeGym) {
                Toast.makeText(getActivity(), "Please, choose different location from yours.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (latitudeGym == 0 && longitudeGym == 0) {
                Toast.makeText(getActivity(), "Please, choose destination point.", Toast.LENGTH_SHORT).show();
                return;
            }

            String uri = "http://maps.google.com/maps?saddr=" + latitudeUser + "," + longitudeUser + "&daddr=" + latitudeGym + "," + longitudeGym;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");

            try {
                getActivity().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                String googleMapsAppPackageName = "com.google.android.apps.maps";
                Intent intentGoogleMaps = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + googleMapsAppPackageName));
                try {
                    startActivity(intentGoogleMaps);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "You need to install Google Play Store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private double latitudeUser = 0;
    private double longitudeUser = 0;
    private double latitudeGym = 0;
    private double longitudeGym = 0;
    private LocationManager locationManager;
    private Location location;
    private GoogleMap gMap;
    private static final int LOCATION_REQUEST_CODE = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setIMapController(iMapController);
        return inflater.inflate(layout.fragment_gmap, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.clear();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, LOCATION_REQUEST_CODE);
            }
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }


        trackLocation();
        addMarksForAllGyms(gMap);

        if (location == null) {
            Toast.makeText(getActivity(), "Location not found", Toast.LENGTH_SHORT).show();
        } else {
            latitudeUser = location.getLatitude();
            longitudeUser = location.getLongitude();
        }

        LatLng marker = new LatLng(latitudeUser, longitudeUser);

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 8));
        gMap.addMarker(new MarkerOptions().title("Your Location").position(marker));

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                latitudeGym = marker.getPosition().latitude;
                longitudeGym = marker.getPosition().longitude;
                return true;
            }
        });
    }

    private void trackLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitudeUser = location.getLatitude();
                    longitudeUser = location.getLongitude();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Location services are off");
        builder.setMessage("Please turn on location services to continue");
        builder.setPositiveButton("Turn on location", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                onMapReady(gMap);
            }
        });

        builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((MainActivity) getActivity()).openGymFragment();
            }
        });
        builder.show();
    }

    private void addMarksForAllGyms(GoogleMap googleMap) {
        for (Gym gym : FitnessManager.getInstance().getAllGyms()) {

            LatLng m = new LatLng(gym.getLatitude(), gym.getLongitude());
            googleMap.addMarker(new MarkerOptions().title(
                    "Name: " + gym.getName() + " Phone number: " +
                            gym.getContactPhoneNumber()).position(m));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(gMap);
            }
        }
    }
}
