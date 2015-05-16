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

    public SensorManagement(Context mContext) {
        this.mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * getAllAvailableSensors
     * @return list of sensors
     */
    public List<Sensor> getAllAvailableSensors() {
        List<Sensor> sensors;
        sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        return sensors;
    }

    /**
     * return selected sensor type
     * @param sensorType
     * @return
     */
    public Sensor getSensorByType(int sensorType) {
        return mSensorManager.getDefaultSensor(sensorType);
    }

    public SensorManager getSensorManager() {
        return mSensorManager;
    }
}
