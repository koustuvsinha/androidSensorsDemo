package com.koustuvsinha.testsensors.adapters;

import android.content.Context;
import android.hardware.Sensor;
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
public class SensorAdapter extends ArrayAdapter<Sensor> {
    public SensorAdapter(Context mContext,List<Sensor> sensorList) {
        super(mContext,0,sensorList);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Sensor mSensor = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item,parent,false);
        }
        TextView sensorName = (TextView)convertView.findViewById(R.id.sensorName);
        sensorName.setText(mSensor.getName());
        return convertView;
    }
}
