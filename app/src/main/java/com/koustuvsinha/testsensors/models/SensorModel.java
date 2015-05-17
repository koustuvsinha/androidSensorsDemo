package com.koustuvsinha.testsensors.models;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by koustuv on 17/5/15.
 */
public class SensorModel extends RealmObject {

    @PrimaryKey
    private String id;

    private String recordDate;
    private int sensorType;
    private float sensorMax1;
    private float sensorMin1;
    private float sensorMax2;
    private float sensorMin2;
    private float sensorMax3;
    private float sensorMin3;

    public SensorModel() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }

    public float getSensorMax1() {
        return sensorMax1;
    }

    public void setSensorMax1(float sensorMax1) {
        this.sensorMax1 = sensorMax1;
    }

    public float getSensorMin1() {
        return sensorMin1;
    }

    public void setSensorMin1(float sensorMin1) {
        this.sensorMin1 = sensorMin1;
    }

    public float getSensorMax2() {
        return sensorMax2;
    }

    public void setSensorMax2(float sensorMax2) {
        this.sensorMax2 = sensorMax2;
    }

    public float getSensorMin2() {
        return sensorMin2;
    }

    public void setSensorMin2(float sensorMin2) {
        this.sensorMin2 = sensorMin2;
    }

    public float getSensorMax3() {
        return sensorMax3;
    }

    public void setSensorMax3(float sensorMax3) {
        this.sensorMax3 = sensorMax3;
    }

    public float getSensorMin3() {
        return sensorMin3;
    }

    public void setSensorMin3(float sensorMin3) {
        this.sensorMin3 = sensorMin3;
    }
}
