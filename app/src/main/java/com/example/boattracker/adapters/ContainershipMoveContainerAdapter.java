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

public class ContainershipMoveContainerAdapter extends BaseAdapter {

    private final Containership containership;
    private final Container container;
    private Context context;
    private List<Containership> containerships;
    private LayoutInflater layoutInflater;

    public ContainershipMoveContainerAdapter(Context context, List<Containership> containerships, Containership containership, Container container) {
        this.context = context;
        this.containerships = containerships;
        this.containership = containership;
        this.container = container;
        this.layoutInflater = LayoutInflater.from(context);
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

        convertView = layoutInflater.inflate(R.layout.adapter_containership_move_container, null);

        Containership containership = (Containership) getItem(position);

        TextView shipNameView = convertView.findViewById(R.id.containership_name);
        shipNameView.setText(containership.getName());

        final double distance = containership.getDistance(this.containership);
        String distanceString;
        if (distance >= 1000) {
            distanceString = (int) Math.round(distance / 1000.0) + " km ";
        } else {
            distanceString = (int) distance + " m ";
        }

        if (containership.isContainershipCloseEnough(this.containership)) {
            distanceString += "(close enough)";
        } else {
            distanceString += "(too far)";
        }

        TextView distanceTextView = convertView.findViewById(R.id.containership_distance);
        distanceTextView.setText(distanceString);

        final int freeVolume = containership.getFreeVolume();
        String volumeString = freeVolume + " m3 free ";
        if (containership.canContainContainer(this.container))
            volumeString += "(free space)";
        else {
            volumeString += "(not enough space)";
        }

        TextView freeVolumeTextView = convertView.findViewById(R.id.containership_free_volume);
        freeVolumeTextView.setText(volumeString);

        convertView.setEnabled(this.containership.canMoveContainerTo(this.container, containership));

        return convertView;
    }
}
