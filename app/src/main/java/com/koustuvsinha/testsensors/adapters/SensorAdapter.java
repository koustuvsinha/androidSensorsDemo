package com.koustuvsinha.testsensors.adapters;

import android.content.Context;
import android.hardware.Sensor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.koustuvsinha.testsensors.R;

import java.util.List;

/**
 * Created by koustuv on 14/5/15.
 */
public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {

    private List<Sensor> sensorList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView sensorNameView;
        public TextView sensorVendorView;
        public ViewHolder(View v) {
            super(v);
            sensorNameView = (TextView)v.findViewById(R.id.sensorName);
            sensorVendorView = (TextView)v.findViewById(R.id.sensorVendor);
        }
    }

    public SensorAdapter(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /*@Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Sensor mSensor = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item,parent,false);
        }
        TextView sensorName = (TextView)convertView.findViewById(R.id.sensorName);
        sensorName.setText(mSensor.getName());
        TextView sensorVendor = (TextView)convertView.findViewById(R.id.sensorVendor);
        sensorVendor.setText(mSensor.getVendor());
        return convertView;
    }*/

    @Override
    public void onBindViewHolder(SensorAdapter.ViewHolder viewHolder, int i) {
        viewHolder.sensorNameView.setText(sensorList.get(i).getName());
        viewHolder.sensorVendorView.setText(sensorList.get(i).getVendor());
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }
}
