package com.group05.emarket.utilities;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.List;

public class DirectionHelper {
    private static final String TAG = DirectionHelper.class.getSimpleName();
    private static final String API_KEY = "AIzaSyBpbbzd_gEfynO4AatkbOuk4DjqGxIa4Vg";

    public static void getDirection(Context context, double originLat, double originLng, double destinationLat, double destinationLng, final MapView mapView) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                new DirectionTask(context, googleMap).execute(originLat, originLng, destinationLat, destinationLng);
            }
        });
    }

    public interface DirectionListener {
        void onDirectionReceived(DirectionsResult directionsResult);

        void onDirectionError();
    }

    private static class DirectionTask extends AsyncTask<Double, Void, DirectionsResult> {
        private Context context;
        private GoogleMap googleMap;

        public DirectionTask(Context context, GoogleMap googleMap) {
            this.context = context;
            this.googleMap = googleMap;
        }

        @Override
        protected DirectionsResult doInBackground(Double... params) {
            GeoApiContext geoApiContext = new GeoApiContext.Builder()
                    .apiKey(API_KEY)
                    .build();

            try {
                double originLat = params[0];
                double originLng = params[1];
                double destinationLat = params[2];
                double destinationLng = params[3];

                return DirectionsApi.newRequest(geoApiContext)
                        .mode(TravelMode.DRIVING)
                        .origin(new com.google.maps.model.LatLng(originLat, originLng))
                        .destination(new com.google.maps.model.LatLng(destinationLat, destinationLng))
                        .await();
            } catch (Exception e) {
                Log.e(TAG, "Error retrieving directions", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(DirectionsResult directionsResult) {
            if (directionsResult != null) {
                displayDirections(directionsResult);
            }
        }

        private void displayDirections(DirectionsResult directionsResult) {
            if (googleMap == null) {
                return;
            }

            googleMap.clear();

            DirectionsRoute route = directionsResult.routes[0];
            DirectionsLeg leg = route.legs[0];

            // Set origin marker
            LatLng originLatLng = new LatLng(leg.startLocation.lat, leg.startLocation.lng);
            googleMap.addMarker(new MarkerOptions()
                    .position(originLatLng)
                    .title("Origin"));

            // Set destination marker
            LatLng destinationLatLng = new LatLng(leg.endLocation.lat, leg.endLocation.lng);
            googleMap.addMarker(new MarkerOptions()
                    .position(destinationLatLng)
                    .title("Destination"));

            // Move the camera to show the route on the map
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 10));

            // Draw the polyline to represent the route
            List<LatLng> points = new ArrayList<>();
            for (com.google.maps.model.LatLng latLng : leg.steps[0].polyline.decodePath()) {
                points.add(new LatLng(latLng.lat, latLng.lng));
            }
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(points)
                    .width(10)
                    .color(Color.BLUE);
            Polyline polyline = googleMap.addPolyline(polylineOptions);
        }
    }
}
