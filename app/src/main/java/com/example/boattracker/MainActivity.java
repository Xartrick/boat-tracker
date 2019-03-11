package com.example.boattracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ContainerShip> bateaux = new ArrayList<>();
        bateaux.add(new ContainerShip("Le Poséïdon","Marcel"));
        bateaux.add(new ContainerShip("Le Zeus","Fred"));
        bateaux.add(new ContainerShip("Le Hadès","Jacques"));
        bateaux.add(new ContainerShip("L'Apollon","Brigitte"));

        ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainerShipAdapter(this, bateaux));

    }
}
