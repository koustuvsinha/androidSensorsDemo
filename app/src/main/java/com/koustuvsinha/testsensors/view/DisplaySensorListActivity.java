package com.koustuvsinha.testsensors.view;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.koustuvsinha.testsensors.R;
import com.koustuvsinha.testsensors.adapters.SensorAdapter;
import com.koustuvsinha.testsensors.listeners.SensorItemClickListener;
import com.koustuvsinha.testsensors.sensors.SensorManagement;
import com.koustuvsinha.testsensors.utils.Constants;

import io.fabric.sdk.android.Fabric;

import java.io.Serializable;
import java.util.List;

public class DisplaySensorListActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_display_sensor_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.listview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final List<Sensor> sensorList = new SensorManagement(this).getAllAvailableSensors();
        mAdapter = new SensorAdapter(this,sensorList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new SensorItemClickListener(this,new SensorItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "Clicked " + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DisplaySensorListActivity.this, DisplayRawSensorDataActivity.class);
                        intent.putExtra(Constants.SELECTED_SENSOR, sensorList.get(position).getType());
                        startActivity(intent);
                    }
                })
        );
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
