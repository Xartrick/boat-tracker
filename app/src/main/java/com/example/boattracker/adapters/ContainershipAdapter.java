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

public class ContainershipAdapter extends BaseAdapter {

    private Context context;
    private List<Containership> containerships;
    private LayoutInflater layoutInflater;

    public ContainershipAdapter(Context context, List<Containership> containerships) {
        this.context = context;
        this.containerships = containerships;
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

        convertView = layoutInflater.inflate(R.layout.adapter_containership, null);

        Containership currentShip = (Containership) getItem(position);
        String shipName = currentShip.getName();
        String shipCaptain = currentShip.getCaptainName();

        TextView shipNameView = convertView.findViewById(R.id.ship_name);
        shipNameView.setText(shipName);

        TextView shipCaptainView = convertView.findViewById(R.id.ship_captain);
        shipCaptainView.setText(shipCaptain);

        TextView numberView = convertView.findViewById(R.id.ship_number);
        numberView.setText(position);

        return convertView;
    }
}
