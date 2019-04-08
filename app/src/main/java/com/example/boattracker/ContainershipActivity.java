package com.example.boattracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.boattracker.models.Containership;
import com.example.boattracker.store.ContainershipStore;

public class ContainershipActivity extends AppCompatActivity {

    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containership);

        parseIntent();
    }

    private void parseIntent() {

        final Intent intent = getIntent();
        final String containershipId = intent.getStringExtra("containership_id");

        ContainershipStore
            .fetch(containershipId)
            .whenComplete((void1, e) -> {

                if (e != null) {
                    Crashlytics.logException(e);
                    return;
                }

                containership = ContainershipStore.get(containershipId);

                drawUI();
            });
    }

    private void drawUI() {

        parseIntent();

        final TextView nameText = findViewById(R.id.text_name);
        nameText.setText(containership.getName());

        final TextView captainText = findViewById(R.id.text_captain_name);
        captainText.setText(containership.getCaptainName());

        final TextView typeText = findViewById(R.id.text_type);
        typeText.setText(containership.getType().getName());

        final TextView portText = findViewById(R.id.text_port);
        portText.setText(containership.getPort().getName());

        final TextView containersText = findViewById(R.id.text_containers);
        String containersCount = Integer.toString(containership.getContainers().size());
        containersText.setText(containersCount);

        final int volume = containership.getContainersVolume();
        final int maxVolume = containership.getType().getVolume();
        final int freeVolume = containership.getFreeVolume();
        String spaceString = volume + " / " + maxVolume + " m3 (" + freeVolume + " m3 free)";

        final TextView spaceText = findViewById(R.id.text_space);
        spaceText.setText(spaceString);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_containership, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                return true;

            case R.id.action_containership_edit:

                final Intent intentEdit = new Intent(getApplicationContext(), EditContainershipActivity.class);
                intentEdit.putExtras(getIntent());

                startActivity(intentEdit);

                return true;

            case R.id.action_containership_edit_containers:

                final Intent intentEditContainers = new Intent(getApplicationContext(), EditContainersActivity.class);
                intentEditContainers.putExtras(getIntent());

                startActivity(intentEditContainers);

                return true;

            case R.id.action_containership_distance:

                final String distance = "Distance : " + containership.getFormattedDistance(containership.getPort());
                Toast.makeText(getApplicationContext(), distance, Toast.LENGTH_SHORT).show();

                return true;

            case R.id.action_containership_map:

                final Intent intentMap = new Intent(getApplicationContext(), ContainershipMapActivity.class);
                intentMap.putExtras(getIntent());

                startActivity(intentMap);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
