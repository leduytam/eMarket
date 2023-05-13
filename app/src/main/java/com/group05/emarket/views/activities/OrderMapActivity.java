package com.group05.emarket.views.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.maps.model.DirectionsResult;
import com.group05.emarket.R;
import com.group05.emarket.utilities.DirectionHelper;

public class OrderMapActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionHelper.DirectionListener {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map);

        // Initialize the MapView
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Call the method to get directions using DirectionHelper
        double originLat = 37.7749;
        double originLng = -122.4194;
        double destinationLat = 34.0522;
        double destinationLng = -118.2437;
        DirectionHelper.getDirection(this, originLat, originLng, destinationLat, destinationLng, mapView);
    }

    @Override
    public void onDirectionReceived(DirectionsResult directionsResult) {
        // Process the directions result here
        // You can access the directions steps, duration, distance, etc.
    }

    @Override
    public void onDirectionError() {
        // Handle error while retrieving directions
    }
}
