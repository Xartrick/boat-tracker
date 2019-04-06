package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.boattracker.adapters.ContainerAdapter;
import com.example.boattracker.models.Containership;
import com.example.boattracker.store.ContainershipStore;

public class EditContainersActivity extends AppCompatActivity {

    private Containership containership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_containers);

        this.parseIntent();
        this.drawUI();
    }

    private void parseIntent() {
        final Intent intent = getIntent();

        final String containership_id = intent.getStringExtra("containership_id");

        // this.containership = (Containership) intent.getSerializableExtra("containership");
        this.containership = ContainershipStore.get(containership_id);
    }

    private void drawUI() {
        final ListView listView = findViewById(R.id.container_list_view);
        listView.setAdapter(new ContainerAdapter(this, this.containership.getContainers()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EditContainershipActivity.class);
        intent.putExtras(getIntent());

        startActivity(intent);
        finish();
    }
}
