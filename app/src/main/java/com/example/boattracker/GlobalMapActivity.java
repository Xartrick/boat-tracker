package com.example.boattracker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.example.boattracker.models.Containership;
import com.example.boattracker.store.ContainershipStore;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

public class GlobalMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

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

        final List<Containership> containerships = ContainershipStore.all();

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Containership containership : containerships) {
            final LatLng position = new LatLng(containership.getLatitude(), containership.getLongitude());
            final MarkerOptions marker = new MarkerOptions().position(position).title(containership.getName());

            map.addMarker(marker);
            builder.include(marker.getPosition());
        }

        final LatLngBounds bounds = builder.build();
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 250);

        map.animateCamera(cu);
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
