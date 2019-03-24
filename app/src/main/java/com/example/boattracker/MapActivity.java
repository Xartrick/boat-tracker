package com.example.boattracker;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.boattracker.models.Containership;
import com.example.boattracker.models.Port;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        parseIntent();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        addMapMarkers();
    }

    private void addMapMarkers() {
        final Containership containership = this.containership;
        final Port port = containership.getPort();

        final LatLng containership_position = new LatLng(containership.getLatitude(), containership.getLongitude());
        final LatLng port_position = new LatLng(port.getLatitude(), port.getLongitude());

        final MarkerOptions containership_marker = new MarkerOptions().position(containership_position).title(containership.getName());
        final MarkerOptions port_marker = new MarkerOptions().position(port_position).title(port.getName());

        this.map.addMarker(containership_marker);
        this.map.addMarker(port_marker);

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        builder.include(containership_marker.getPosition());
        builder.include(port_marker.getPosition());

        final LatLngBounds bounds = builder.build();
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 250);

        this.map.animateCamera(cu);
    }

    private void parseIntent() {
        final Intent intent = getIntent();

        this.containership = (Containership) intent.getSerializableExtra("containership");
    }
}