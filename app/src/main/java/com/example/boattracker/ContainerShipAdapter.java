package com.example.boattracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContainerShipAdapter extends BaseAdapter {

    private Context context;
    private List<ContainerShip> shipList;
    private LayoutInflater layoutInflater;

    public ContainerShipAdapter(Context context, List<ContainerShip> shipList) {
        this.context = context;
        this.shipList = shipList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shipList.size();
    }

    @Override
    public Object getItem(int position) {
        return shipList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.adapter_containership, null);

        ContainerShip currentShip = (ContainerShip) getItem(position);
        String shipName = currentShip.getName();
        String shipCaptain = currentShip.getCaptainName();

        TextView shipNameView = convertView.findViewById(R.id.ship_name);
        shipNameView.setText(shipName);

        TextView shipCaptainView = convertView.findViewById(R.id.ship_captain);
        shipCaptainView.setText(shipCaptain);

        TextView numberView = convertView.findViewById(R.id.ship_number);
        Integer pos = position;
        numberView.setText(pos.toString());

        return convertView;
    }
}
