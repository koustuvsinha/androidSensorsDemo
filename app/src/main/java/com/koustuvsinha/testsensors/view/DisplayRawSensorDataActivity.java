package com.koustuvsinha.testsensors.view;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
import com.koustuvsinha.testsensors.adapters.ViewerPagerAdapter;
import com.koustuvsinha.testsensors.models.SensorModel;
import com.koustuvsinha.testsensors.sensors.SensorManagement;
import com.koustuvsinha.testsensors.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DisplayRawSensorDataActivity extends FragmentActivity implements DisplayRawSensorData.OnFragmentInteractionListener,DisplaySensorHistoryData.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_raw_sensor_data);
        generateViews();

    }

    private void generateViews() {
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(DisplayRawSensorData.newInstance(getIntent().getIntExtra(Constants.SELECTED_SENSOR,0)));
        fragments.add(DisplaySensorHistoryData.newInstance(getIntent().getIntExtra(Constants.SELECTED_SENSOR,0)));

        viewPager.setAdapter(new ViewerPagerAdapter(getSupportFragmentManager(), fragments));
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(Constants.TAG_NAME,uri.toString());
    }
}
