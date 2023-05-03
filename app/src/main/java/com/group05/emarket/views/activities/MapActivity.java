package com.group05.emarket.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.group05.emarket.R;
import com.group05.emarket.repositories.AddressRepository;
import com.group05.emarket.views.adapters.GoogleMapPredictionAdapter;
import com.group05.emarket.views.dialogs.LocationBottomSheetDialog;

import java.io.IOException;
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

    private boolean isHavingDefaultAddress = false;

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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isHavingDefaultAddress = extras.getBoolean("isHavingDefaultAddress");
        }

        if (!isHavingDefaultAddress) {
            locationBottomSheetDialog.setDisable(true);
        }

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

    private void getFromLocationLongLat(double latitude, double longitude) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1, addresses -> {
                    setCurrentAddress(addresses.get(0));
                });
            } else {
                List<Address> addressList = new ArrayList<>();
                addressList = geocoder.getFromLocation(latitude, longitude, 1);
                setCurrentAddress(addressList.get(0));
            }
        } catch (IOException e) {
            Log.e("getFromLocationLongLat", e.getMessage());
            e.printStackTrace();
        }

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        AddressRepository addressRepository = AddressRepository.getInstance();
        addressRepository.getUserAddress().thenAccept(addressModel -> {
            if (addressModel != null) {
                double latitude = addressModel.getLatitude();
                double longitude = addressModel.getLongitude();
                getFromLocationLongLat(latitude, longitude);
            } else {
                FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                mFusedLocationClient.getCurrentLocation(100, null).addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            getFromLocationLongLat(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
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
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
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
                if (query != null) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Log.d("onQueryTextSubmit", "onQueryTextSubmit:  " + query);
                            geocoder.getFromLocationName(query, 1, addresses -> {
                                if (addresses.size() > 0) {
                                    setCurrentAddress(addresses.get(0));
                                }
                            });
                        } else {
                            Log.d("onQueryTextSubmit", "onQueryTextSubmit: - < 33 " + query);
                            List<Address> addressList = geocoder.getFromLocationName(query, 1);
                            if (addressList.size() > 0) {
                                setCurrentAddress(addressList.get(0));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address.getAddressLine(0));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        mMap.clear();
        var locationMarker = mMap.addMarker(markerOptions);
        if (locationMarker != null) {
            locationMarker.showInfoWindow();
            locationMarker.setDraggable(true);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM));
        binding.tvCurrentMapLocation.setText(address.getAddressLine(0));
        locationBottomSheetDialog.setAddress(address);
        binding.svMapSearch.clearFocus();
    }

}