package com.example.boattracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.boattracker.adapters.ContainershipAdapter;
import com.example.boattracker.models.Containership;
import com.example.boattracker.store.ContainerStore;
import com.example.boattracker.store.ContainershipStore;
import com.example.boattracker.store.ContainershipTypeStore;
import com.example.boattracker.store.PortStore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
    }

    private void getData() {

        PortStore
            .fetch()
            .whenComplete((void1, e1) -> {

                if (e1 != null) {
                    Crashlytics.logException(e1);

                    return;
                }

                ContainershipTypeStore.fetch().whenComplete((void2, e2) -> {

                    if (e2 != null) {
                        Crashlytics.logException(e2);

                        return;
                    }

                    ContainershipStore.fetch().whenComplete((void3, e3) -> {

                        if (e3 != null) {
                            Crashlytics.logException(e3);

                            return;
                        }

                        ContainerStore.fetch().whenComplete((void4, e4) -> {

                            if (e4 != null) {
                                Crashlytics.logException(e4);

                                return;
                            }

                            drawUI();
                        });
                    });
                });
            });
    }

    private void drawUI() {

        final ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainershipAdapter(this, ContainershipStore.all()));

        Toast.makeText(this, "UI updated!", Toast.LENGTH_SHORT).show();

        shipListView.setOnItemClickListener((parent, view, position, id) -> {

            final Containership containership = ContainershipStore.all().get(position);

            final Intent intent = new Intent(getApplicationContext(), ContainershipActivity.class);
            intent.putExtra("containership_id", containership.getId());

            startActivity(intent);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_refresh:

                this.getData();

                return true;

            case R.id.action_login:

                Intent sign_in = new Intent(getApplicationContext(), SignInActivity.class);

                startActivity(sign_in);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
