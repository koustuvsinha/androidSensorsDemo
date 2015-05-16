package com.koustuvsinha.testsensors.view;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.koustuvsinha.testsensors.R;
import com.koustuvsinha.testsensors.sensors.SensorManagement;
import com.koustuvsinha.testsensors.utils.Constants;

public class DisplayRawSensorDataActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    //private TextView detailsDataValuesView;
    private TextView detailsData1View;
    private TextView detailsData2View;
    private TextView detailsData3View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_raw_sensor_data);

        SensorManagement sensorManagement = new SensorManagement(this);
        mSensor =  sensorManagement.getSensorByType(getIntent().getIntExtra(Constants.SELECTED_SENSOR,0));
        mSensorManager = sensorManagement.getSensorManager();
        TextView detailsTextView = (TextView)findViewById(R.id.sensorDetailsName);
        detailsTextView.setText(mSensor.getName());
        //detailsDataValuesView = (TextView)findViewById(R.id.sensorDetailsValues);
        detailsData1View = (TextView)findViewById(R.id.sensorDetailsData1);
        detailsData2View = (TextView)findViewById(R.id.sensorDetailsData2);
        detailsData3View = (TextView)findViewById(R.id.sensorDetailsData3);
        //detailsDataView.setText('0');
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

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        int len = event.values.length;
        Log.i("APP","Length of values : " + len);

        //detailsDataValuesView.setText(Integer.toString(len));
        if(len == 3) {
            detailsData1View.setText(Float.toString(event.values[0]));
            detailsData2View.setText(Float.toString(event.values[1]));
            detailsData3View.setText(Float.toString(event.values[2]));
        }
        if(len == 2) {
            detailsData1View.setText(Float.toString(event.values[0]));
            detailsData2View.setText(Float.toString(event.values[1]));
        }
        if(len == 1) {
            detailsData1View.setText(Float.toString(event.values[0]));
        }
        // Do something with this sensor value.
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        Log.i("APP","Unregistered sensor listener");
    }
}
