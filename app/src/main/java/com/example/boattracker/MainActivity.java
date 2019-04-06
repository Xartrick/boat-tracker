package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.example.boattracker.adapters.ContainershipAdapter;
import com.example.boattracker.store.ContainershipStore;
import com.example.boattracker.store.ContainershipTypeStore;
import com.example.boattracker.store.PortStore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.signInButton).setOnClickListener(v -> {
            Intent sign_in = new Intent(getApplicationContext(), SignInActivity.class);

            startActivity(sign_in);
        });

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

                        drawUI();
                    });
                });
            });
    }

    private void drawUI() {
        final ListView shipListView = findViewById(R.id.ship_list_view);
        shipListView.setAdapter(new ContainershipAdapter(this, ContainershipStore.all()));

        shipListView.setOnItemClickListener((parent, view, position, id) -> {
            final Intent intent = new Intent(getApplicationContext(), ContainershipActivity.class);
            intent.putExtra("containership", ContainershipStore.all().get(position));

            startActivity(intent);
        });
    }
}
