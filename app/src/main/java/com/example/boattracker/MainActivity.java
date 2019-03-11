package com.example.boattracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boattracker.adapters.ContainershipAdapter;
import com.example.boattracker.models.Containership;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Containership> bateaux = new ArrayList<>();
        bateaux.add(new Containership("Le Poséïdon","Marcel"));
        bateaux.add(new Containership("Le Zeus","Fred"));
        bateaux.add(new Containership("Le Hadès","Jacques"));
        bateaux.add(new Containership("L'Apollon","Brigitte"));

        ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainershipAdapter(this, bateaux));

        shipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), bateaux.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
