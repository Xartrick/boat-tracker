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

class ContainerViewHolder {
    TextView lengthTextView;
    TextView idTextView;
}

public class ContainerAdapter extends BaseAdapter {

    private final List<Container> containers;
    private final LayoutInflater layoutInflater;

    public ContainerAdapter(Context context, List<Container> containers) {

        this.containers = containers;

        layoutInflater = LayoutInflater.from(context);
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

        final ContainerViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_container, parent, false);

            viewHolder = new ContainerViewHolder();
            viewHolder.lengthTextView = convertView.findViewById(R.id.container_volume);
            viewHolder.idTextView = convertView.findViewById(R.id.container_id);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ContainerViewHolder) convertView.getTag();
        }

        final Container container = (Container) getItem(position);

        final String text = Integer.toString(container.getLength()) +
            "x" +
            Integer.toString(container.getWidth()) +
            "x" +
            Integer.toString(container.getHeight()) +
            " (" + container.getVolume() + " m3)";

        viewHolder.lengthTextView.setText(text);
        viewHolder.idTextView.setText(container.getId());

        return convertView;
    }
}
