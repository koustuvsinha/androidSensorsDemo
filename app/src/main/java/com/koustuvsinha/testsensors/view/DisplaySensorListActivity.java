package com.koustuvsinha.testsensors.view;

import android.hardware.Sensor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.koustuvsinha.testsensors.R;
import com.koustuvsinha.testsensors.adapters.SensorAdapter;
import com.koustuvsinha.testsensors.sensors.SensorManagement;

import io.fabric.sdk.android.Fabric;
import java.util.List;

public class DisplaySensorListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_display_sensor_list);

        final ListView listview = (ListView) findViewById(R.id.listview);
        List<Sensor> sensorList = new SensorManagement().getAllAvailableSensors(this);
        SensorAdapter sensorAdapter = new SensorAdapter(this,sensorList);
        listview.setAdapter(sensorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_sensor_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
