package com.example.boattracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boattracker.R;
import com.example.boattracker.models.Container;

import java.util.List;

public class ContainerAdapter extends BaseAdapter {

    private Context context;
    private List<Container> containers;
    private LayoutInflater layoutInflater;

    public ContainerAdapter(Context context, List<Container> containers) {
        this.context = context;
        this.containers = containers;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return containers.size();
    }

    @Override
    public Object getItem(int position) {
        return containers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.adapter_container, null);

        Container container = (Container) getItem(position);

        TextView lengthTextView = convertView.findViewById(R.id.container_volume);
        lengthTextView.setText(Integer.toString(container.getLength()) + "x" + Integer.toString(container.getWidth()) + "x" + Integer.toString(container.getHeight()) + " (" + container.getVolume() + " m3)");

        TextView idTextView = convertView.findViewById(R.id.container_id);
        idTextView.setText(container.getId());

        return convertView;
    }
}
