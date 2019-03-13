package com.example.boattracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;
import com.example.boattracker.models.Port;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Port port = new Port("1", "Port", 0, 0);
        ContainershipType type = new ContainershipType("1", "Type", 1, 1, 1);
        Containership containership = new Containership("1", "Le Poséïdon","Marcel", -34, 151, port, type);

        addMapMarker(containership);
    }

    private void addMapMarker(Containership containership) {
        LatLng position = new LatLng(containership.getLatitude(), containership.getLongitude());

        mMap.addMarker(new MarkerOptions().position(position).title(containership.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
