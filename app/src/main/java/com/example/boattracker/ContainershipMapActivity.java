package com.example.boattracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.example.boattracker.models.Containership;
import com.example.boattracker.models.Port;
import com.example.boattracker.store.ContainershipStore;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class ContainershipMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        addMapMarkers();
    }

    private void addMapMarkers() {

        parseIntent();

        final Port port = containership.getPort();

        final LatLng containershipPosition = new LatLng(containership.getLatitude(), containership.getLongitude());
        final LatLng portPosition = new LatLng(port.getLatitude(), port.getLongitude());

        final MarkerOptions containershipMarker = new MarkerOptions().position(containershipPosition).title(containership.getName());
        final MarkerOptions portMarker = new MarkerOptions().position(portPosition).title(port.getName());

        map.addMarker(containershipMarker);
        map.addMarker(portMarker);

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        builder.include(containershipMarker.getPosition());
        builder.include(portMarker.getPosition());

        final LatLngBounds bounds = builder.build();
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 250);

        map.animateCamera(cu);
    }

    private void parseIntent() {

        final Intent intent = getIntent();

        final String containershipId = intent.getStringExtra("containership_id");
        containership = ContainershipStore.get(containershipId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {

                onBackPressed();

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
