package com.koustuvsinha.testsensors.sensors;

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
    public List<Sensor> getAllAvailableSensors() {
        List<Sensor> sensors;
        sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        return sensors;
    }
}
