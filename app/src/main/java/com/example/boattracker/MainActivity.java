package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boattracker.adapters.ContainershipAdapter;
import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;
import com.example.boattracker.models.Port;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContainershipType type = new ContainershipType("1", "Type", 10, 10, 10);
        Port port = new Port("1", "Marseille", 43.296482, 5.369780); // Coordonnées réelles de Marseille en degrés

        final List<Containership> bateaux = new ArrayList<>();
        bateaux.add(new Containership("1", "Le Poséïdon", "Marcel", 43.524910, 5.454140, port, type)); // Coordonnées réelles ... d'Aix-en-Provence en degrés
        bateaux.add(new Containership("2", "Le Zeus", "Fred", 0, 0, port, type));
        bateaux.add(new Containership("3", "Le Hadès", "Jacques", 0, 0, port, type));
        bateaux.add(new Containership("4", "L'Apollon", "Brigitte", 0, 0, port, type));

        ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainershipAdapter(this, bateaux));

        shipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), bateaux.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent ship_detail = new Intent(getApplicationContext(), ShipDetail.class);
                ship_detail.putExtra("containership", bateaux.get(position));
                startActivity(ship_detail);
                finish();
            }
        });

        findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_in = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(sign_in);
                finish();
            }
        });

    }
}
