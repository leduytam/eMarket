package com.group05.emarket.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.group05.emarket.databinding.ActivityMapBinding;

import android.Manifest;
import android.util.Log;

import androidx.appcompat.widget.SearchView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.group05.emarket.R;
import com.group05.emarket.views.dialogs.LocationBottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_CODE_GPS_PERMISSION = 100;
    private static final int MAP_ZOOM = 1000;

    private Geocoder geocoder;
    private ActivityMapBinding binding;
    private LocationBottomSheetDialog locationBottomSheetDialog;
    private Address currentAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationBottomSheetDialog = new LocationBottomSheetDialog(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_app);
        geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
        binding.topBar.setNavigationOnClickListener(v -> finish());
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS_PERMISSION);
        }
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location == null) {
                    return;
                }
                List<Address> addressList = new ArrayList<>();
                try {
                    addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addressList.size() > 0) {
                        setCurrentAddress(addressList.get(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_GPS_PERMISSION:
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                break;
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        binding.svMapSearch.setIconifiedByDefault(false);
        binding.svMapSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = query;
                if (location != null && !location.equals("")) {
                    if (location.isEmpty()) {
                        getCurrentLocation();
                    } else {
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                geocoder.getFromLocationName(location, 1, new Geocoder.GeocodeListener() {
                                    @Override
                                    public void onGeocode(@NonNull List<Address> addresses) {
                                        if (addresses.size() > 0) {
                                            setCurrentAddress(addresses.get(0));
                                        }
                                    }
                                });
                            } else {
                                List<Address> addressList = geocoder.getFromLocationName(location, 1);
                                if (addressList.size() > 0) {
                                    setCurrentAddress(addressList.get(0));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    getCurrentLocation();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.btnMapConfirm.setOnClickListener(v -> {
            locationBottomSheetDialog.show();
        });
    }

    void setCurrentAddress(Address address) {
        currentAddress = address;
        LatLng latLng = new LatLng(currentAddress.getLatitude(), currentAddress.getLongitude());
        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address.getAddressLine(0));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        var locationMarker= mMap.addMarker(markerOptions);
        locationMarker.showInfoWindow();
        locationMarker.setDraggable(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM));
        binding.tvCurrentMapLocation.setText(address.getAddressLine(0));
        locationBottomSheetDialog.setAddress(address);
        binding.svMapSearch.clearFocus();
    }

}