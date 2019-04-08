package com.example.boattracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boattracker.R;
import com.example.boattracker.models.Container;
import com.example.boattracker.models.Containership;

import java.util.List;

class ContainershipMoveContainerViewHolder {
    TextView shipNameView;
    TextView distanceTextView;
    TextView freeVolumeTextView;
}

public class ContainershipMoveContainerAdapter extends BaseAdapter {

    private final List<Containership> containerships;
    private final Containership containership;
    private final Container container;
    private final LayoutInflater layoutInflater;

    public ContainershipMoveContainerAdapter(Context context, List<Containership> containerships, Containership containership, Container container) {
        this.containerships = containerships;
        this.containership = containership;
        this.container = container;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return containerships.size();
    }

    @Override
    public Object getItem(int position) {
        return containerships.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ContainershipMoveContainerViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_containership_move_container, parent, false);

            viewHolder = new ContainershipMoveContainerViewHolder();
            viewHolder.shipNameView = convertView.findViewById(R.id.containership_name);
            viewHolder.distanceTextView = convertView.findViewById(R.id.containership_distance);
            viewHolder.freeVolumeTextView = convertView.findViewById(R.id.containership_free_volume);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ContainershipMoveContainerViewHolder) convertView.getTag();
        }

        final Containership containership = (Containership) getItem(position);

        viewHolder.shipNameView.setText(containership.getName());

        final String distanceString = containership.getFormattedDistance(containership);
        viewHolder.distanceTextView.setText(distanceString);

        final String volumeString = containership.getFreeVolume() + " m3 free";
        viewHolder.freeVolumeTextView.setText(volumeString);

        return convertView;
    }
}
