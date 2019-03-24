package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.boattracker.adapters.ContainershipAdapter;
import com.example.boattracker.models.Containership;
import com.example.boattracker.models.ContainershipType;
import com.example.boattracker.models.Port;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Containership> containerships;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        drawUI();
    }

    private void getData() {
        ContainershipType type = new ContainershipType("1", "Type", 10, 10, 10);
        Port port = new Port("1", "Marseille", 43.296482, 5.369780); // Marseille

        this.containerships = new ArrayList<>();

        this.containerships.add(new Containership("1", "Le Poséïdon", "Marcel", 43.524910, 5.454140, port, type)); // Aix-en-Provence
        this.containerships.add(new Containership("2", "Le Zeus", "Fred", 0, 0, port, type));
        this.containerships.add(new Containership("3", "Le Hadès", "Jacques", 0, 0, port, type));
        this.containerships.add(new Containership("4", "L'Apollon", "Brigitte", 0, 0, port, type));
    }

    private void drawUI() {
        final ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainershipAdapter(this, this.containerships));

        shipListView.setOnItemClickListener((parent, view, position, id) -> {
            final Intent intent = new Intent(getApplicationContext(), ContainershipActivity.class);
            intent.putExtra("containership", this.containerships.get(position));

            startActivity(intent);

            finish();
        });
    }
}
