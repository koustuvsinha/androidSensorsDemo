package com.koustuvsinha.testsensors.view;

import android.app.Activity;
import android.hardware.Sensor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.koustuvsinha.testsensors.R;
import com.koustuvsinha.testsensors.sensors.SensorManagement;
import com.koustuvsinha.testsensors.utils.Constants;

public class DisplayRawSensorDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_raw_sensor_data);

        SensorManagement sensorManagement = new SensorManagement(this);
        Sensor sensor =  sensorManagement.getSensorByType(getIntent().getIntExtra(Constants.SELECTED_SENSOR,0));
        TextView detailsTextView = (TextView)findViewById(R.id.sensorDetails);
        detailsTextView.setText(sensor.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_raw_sensor_data, menu);
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
