package com.example.boattracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boattracker.R;
import com.example.boattracker.models.Containership;

import java.util.List;

class ContainershipViewHolder {
    TextView shipNameView;
    TextView shipCaptainView;
}

public class ContainershipAdapter extends BaseAdapter {

    private final List<Containership> containerships;
    private final LayoutInflater layoutInflater;

    public ContainershipAdapter(Context context, List<Containership> containerships) {

        this.containerships = containerships;

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

        final ContainershipViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_containership, parent, false);

            viewHolder = new ContainershipViewHolder();
            viewHolder.shipNameView = convertView.findViewById(R.id.containership_name);
            viewHolder.shipCaptainView = convertView.findViewById(R.id.containership_type_name);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ContainershipViewHolder) convertView.getTag();
        }

        final Containership containership = (Containership) getItem(position);

        viewHolder.shipNameView.setText(containership.getName());
        viewHolder.shipCaptainView.setText(containership.getType().getName());

        return convertView;
    }
}
