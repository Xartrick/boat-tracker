package com.example.boattracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boattracker.adapters.ContainerAdapter;
import com.example.boattracker.adapters.ContainershipMoveContainerAdapter;
import com.example.boattracker.models.Container;
import com.example.boattracker.models.Containership;
import com.example.boattracker.store.ContainerStore;
import com.example.boattracker.store.ContainershipStore;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditContainersActivity extends AppCompatActivity {

    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_containers);

        drawUI();
    }

    private void parseIntent() {

        final Intent intent = getIntent();
        final String containershipId = intent.getStringExtra("containership_id");

        containership = ContainershipStore.get(containershipId);
    }

    private void drawUI() {

        parseIntent();

        final List<Container> containers = containership.getContainers();

        final ListView listView = findViewById(R.id.container_list_view);
        listView.setAdapter(new ContainerAdapter(this, containers));
        listView.setEmptyView(findViewById(R.id.containers_empty));

        listView.setOnItemClickListener((parent, view, position, id) -> {

            final Container container = containers.get(position);
            final List<Containership> availableContainerships = new ArrayList<>();

            final List<Containership> containerships = ContainershipStore.all();
            for (Containership c : containerships) {
                if (container.getContainership().is(c)) {
                    continue;
                }

                if (!containership.isContainershipCloseEnough(c)) {
                    continue;
                }

                if (!containership.canContainContainer(container)) {
                    continue;
                }

                availableContainerships.add(c);
            }

            if (availableContainerships.size() == 0) {
                Toast.makeText(this, "No available containership.", Toast.LENGTH_SHORT).show();

                return;
            }

            final ContainershipMoveContainerAdapter adapter = new ContainershipMoveContainerAdapter(this, availableContainerships, containership, container);

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                .setTitle("Move container to")
                .setAdapter(adapter, (dialog, which) -> {

                    final Containership containership = availableContainerships.get(which);
                    final Containership source = container.getContainership();

                    if (!containership.isContainershipCloseEnough(source)) {
                        Toast.makeText(this, "Containership is too far.", Toast.LENGTH_SHORT).show();

                        drawUI();

                        return;
                    }

                    if (!containership.moveContainerFromContainership(source, container)) {
                        Toast.makeText(this, "Cannot move container to this containership.", Toast.LENGTH_SHORT).show();

                        drawUI();

                        return;
                    }

                    FirebaseFirestore
                        .getInstance()
                        .collection(Container.COLLECTION_NAME)
                        .document(container.getId())
                        .update(container.getData())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Container moved!", Toast.LENGTH_SHORT).show();

                            drawUI();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to move container.", Toast.LENGTH_SHORT).show();

                            drawUI();
                        });
                });

            builder.create().show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_edit_containers, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                return true;

            case R.id.action_edit_container_add:

                final List<Container> containers = ContainerStore.allWithoutContainership();

                if (containers.size() == 0) {
                    Toast.makeText(this, "No container available.", Toast.LENGTH_SHORT).show();

                    return true;
                }

                final ContainerAdapter adapter = new ContainerAdapter(this, containers);

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                    .setTitle("Add container")
                    .setAdapter(adapter, (dialog, which) -> {
                        final Container container = containers.get(which);

                        if (!containership.addContainer(container)) {
                            Toast.makeText(this, "Failed to add container.", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        FirebaseFirestore
                            .getInstance()
                            .collection(Container.COLLECTION_NAME)
                            .document(container.getId())
                            .update(container.getData())
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Container added!", Toast.LENGTH_SHORT).show();

                                drawUI();
                            })
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to add container.", Toast.LENGTH_SHORT).show());
                    });

                builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
