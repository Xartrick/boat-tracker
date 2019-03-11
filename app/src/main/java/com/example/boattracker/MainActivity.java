package com.example.boattracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<ContainerShip> bateaux = new ArrayList<>();
        bateaux.add(new ContainerShip("Le Poséïdon","Marcel"));
        bateaux.add(new ContainerShip("Le Zeus","Fred"));
        bateaux.add(new ContainerShip("Le Hadès","Jacques"));
        bateaux.add(new ContainerShip("L'Apollon","Brigitte"));

        ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainerShipAdapter(this, bateaux));

        shipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), bateaux.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
