package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boattracker.models.Containership;
import com.example.boattracker.store.ContainershipStore;

public class ContainershipActivity extends AppCompatActivity {

    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containership);

        parseIntent();
        drawUI();
    }

    private void parseIntent() {
        final Intent intent = getIntent();

        final String containership_id = intent.getStringExtra("containership_id");

        // this.containership = (Containership) intent.getSerializableExtra("containership");
        this.containership = ContainershipStore.get(containership_id);
    }

    private void drawUI() {
        final Containership containership = this.containership;

        final TextView name_text_view = findViewById(R.id.text_name);
        name_text_view.setText(containership.getName());

        final TextView captain_name_text_view = findViewById(R.id.text_captain_name);
        captain_name_text_view.setText(containership.getCaptainName());

        final TextView type_text_view = findViewById(R.id.text_type);
        type_text_view.setText(containership.getType().getName());

        final TextView port_text_view = findViewById(R.id.text_port);
        port_text_view.setText(containership.getPort().getName());

        final TextView containers_text_view = findViewById(R.id.text_containers);
        containers_text_view.setText(containership.getContainers().size() + " containers");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_containership, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_containership_edit:
                final Intent intentEdit = new Intent(getApplicationContext(), EditContainershipActivity.class);
                intentEdit.putExtras(getIntent());

                startActivity(intentEdit);
                finish();

                return true;

            case R.id.action_containership_distance:
                final double distance = containership.getDistance(containership.getPort());

                String distanceToDisplay;

                if (distance >= 1000){
                    distanceToDisplay = "Distance : " + Math.round(distance / 1000) + " km";
                } else {
                    distanceToDisplay = "Distance : " + distance + " m";
                }

                Toast.makeText(getApplicationContext(), distanceToDisplay, Toast.LENGTH_SHORT).show();

                return true;

            case R.id.action_containership_map:
                final Intent intentMap = new Intent(getApplicationContext(), MapActivity.class);
                intentMap.putExtras(getIntent());

                startActivity(intentMap);
                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
