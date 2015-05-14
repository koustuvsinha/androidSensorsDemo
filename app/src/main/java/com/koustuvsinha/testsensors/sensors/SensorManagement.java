package com.koustuvsinha.testsensors.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by koustuvs on 5/14/2015.
 */
public class SensorManagement {
    private SensorManager mSensorManager;

    /**
     * getAllAvailableSensors
     * @return list of sensors
     */
    public List<Sensor> getAllAvailableSensors(Context mContext) {
        List<Sensor> sensors;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        return sensors;
    }
}
