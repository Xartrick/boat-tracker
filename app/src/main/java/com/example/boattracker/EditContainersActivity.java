package com.example.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.boattracker.adapters.ContainerAdapter;
import com.example.boattracker.models.Containership;

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

        this.containership = (Containership) intent.getSerializableExtra("containership");
    }

    private void drawUI() {
        final ListView listView = findViewById(R.id.container_list_view);
        listView.setAdapter(new ContainerAdapter(this, this.containership.getContainers()));
    }
}
