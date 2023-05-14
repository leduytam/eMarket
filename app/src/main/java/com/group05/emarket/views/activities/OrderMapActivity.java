package com.group05.emarket.views.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.maps.model.DirectionsResult;
import com.group05.emarket.R;
import com.group05.emarket.databinding.ActivityOrderMapBinding;
import com.group05.emarket.utilities.DirectionHelper;

public class OrderMapActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionHelper.DirectionListener {
    private MapView mapView;
    ActivityOrderMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map);
        binding = ActivityOrderMapBinding.inflate(getLayoutInflater());
        binding.topBar.setNavigationOnClickListener(v -> finish());

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
        Intent intent = getIntent();
        // get the order: lat, lng
        double orderLat = intent.getDoubleExtra("orderLat", 0);
        double orderLng = intent.getDoubleExtra("orderLng", 0);

        // get the delivery: lat, lng
        double deliveryLat = intent.getDoubleExtra("deliveryLat", 0);
        double deliveryLng = intent.getDoubleExtra("deliveryLng", 0);
        DirectionHelper.getDirection(this, orderLat, orderLng, deliveryLat, deliveryLng, mapView);
    }

    @Override
    public void onDirectionReceived(DirectionsResult directionsResult) {
    }

    @Override
    public void onDirectionError() {
    }
}
