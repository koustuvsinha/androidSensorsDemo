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
import com.koustuvsinha.testsensors.models.SensorModel;
import com.koustuvsinha.testsensors.sensors.SensorManagement;
import com.koustuvsinha.testsensors.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DisplayRawSensorDataActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    //private TextView detailsDataValuesView;
    private TextView detailsData1View;
    private TextView detailsData2View;
    private TextView detailsData3View;
    private TextView detailsData1MaxView;
    private TextView detailsData2MaxView;
    private TextView detailsData3MaxView;
    private TextView detailsData1MinView;
    private TextView detailsData2MinView;
    private TextView detailsData3MinView;
    private SensorModel sensorModel;
    private float sensorMax1;
    private float sensorMax2;
    private float sensorMax3;
    private float sensorMin1;
    private float sensorMin2;
    private float sensorMin3;
    private boolean isFirst;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_raw_sensor_data);

        // create realm instance
        realm = Realm.getInstance(this);

        SensorManagement sensorManagement = new SensorManagement(this);
        mSensor =  sensorManagement.getSensorByType(getIntent().getIntExtra(Constants.SELECTED_SENSOR,0));
        mSensorManager = sensorManagement.getSensorManager();
        TextView detailsTextView = (TextView)findViewById(R.id.sensorDetailsName);
        detailsTextView.setText(mSensor.getName());
        TextView detailsVendorView = (TextView)findViewById(R.id.sensorVendor);
        detailsVendorView.setText(mSensor.getVendor());
        //detailsDataValuesView = (TextView)findViewById(R.id.sensorDetailsValues);
        detailsData1View = (TextView)findViewById(R.id.sensorDetailsData1);
        detailsData2View = (TextView)findViewById(R.id.sensorDetailsData2);
        detailsData3View = (TextView)findViewById(R.id.sensorDetailsData3);
        //detailsDataView.setText('0');
        detailsData1MaxView = (TextView)findViewById(R.id.maxData1);
        detailsData2MaxView = (TextView)findViewById(R.id.maxData2);
        detailsData3MaxView = (TextView)findViewById(R.id.maxData3);

        detailsData1MinView = (TextView)findViewById(R.id.minData1);
        detailsData2MinView = (TextView)findViewById(R.id.minData2);
        detailsData3MinView = (TextView)findViewById(R.id.minData3);

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

        // Do something with this sensor value.
        // set the min and max

        //extremely shitty implementation, had to do it because realm does not
        //support arrays yet

        if(isFirst) {
            sensorMax1 = sensorMin1 = event.values[0];
            sensorMax2 = sensorMin1 = event.values[1];
            sensorMax3 = sensorMin1 = event.values[2];
            isFirst = false;
        }

        sensorMax1 = sensorMax1 > event.values[0] ? sensorMax1 : event.values[0];
        sensorMax2 = sensorMax2 > event.values[1] ? sensorMax2 : event.values[1];
        sensorMax3 = sensorMax3 > event.values[2] ? sensorMax3 : event.values[2];
        sensorMin1 = sensorMin1 < event.values[0] ? sensorMin1 : event.values[0];
        sensorMin2 = sensorMin2 < event.values[1] ? sensorMin2 : event.values[1];
        sensorMin3 = sensorMin3 < event.values[2] ? sensorMin3 : event.values[2];


        if(len == 3) {
            detailsData1View.setText(Float.toString(event.values[0]));
            detailsData2View.setText(Float.toString(event.values[1]));
            detailsData3View.setText(Float.toString(event.values[2]));
            detailsData1MaxView.setText(Float.toString(sensorMax1));
            detailsData2MaxView.setText(Float.toString(sensorMax2));
            detailsData3MaxView.setText(Float.toString(sensorMax3));
            detailsData1MinView.setText(Float.toString(sensorMin1));
            detailsData2MinView.setText(Float.toString(sensorMin2));
            detailsData3MinView.setText(Float.toString(sensorMin3));
        }
        if(len == 2) {
            detailsData1View.setText(Float.toString(event.values[0]));
            detailsData2View.setText(Float.toString(event.values[1]));
            detailsData1MaxView.setText(Float.toString(sensorMax1));
            detailsData2MaxView.setText(Float.toString(sensorMax2));
            detailsData1MinView.setText(Float.toString(sensorMin1));
            detailsData2MinView.setText(Float.toString(sensorMin2));
        }
        if(len == 1) {
            detailsData1View.setText(Float.toString(event.values[0]));
            detailsData1MaxView.setText(Float.toString(sensorMax1));
            detailsData1MinView.setText(Float.toString(sensorMin1));
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        RealmQuery<SensorModel> query = realm.where(SensorModel.class);
        // convert today's date in String
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        query.equalTo("recordDate",todayDate);
        query.equalTo("sensorType",mSensor.getType());
        RealmResults<SensorModel> result = query.findAll();
        if(result.size() > 0) {
            sensorModel =  result.get(0);

            Log.i(Constants.TAG_NAME,"Record found, loading the record");

            sensorMax1 = sensorModel.getSensorMax1();
            sensorMax2 = sensorModel.getSensorMax2();
            sensorMax3 = sensorModel.getSensorMax3();
            sensorMin1 = sensorModel.getSensorMin1();
            sensorMin2 = sensorModel.getSensorMin2();
            sensorMin3 = sensorModel.getSensorMin3();

        } else {
            sensorModel = new SensorModel();
            Log.i(Constants.TAG_NAME,"Record not found, intializing the record");
            isFirst = true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        realm = Realm.getInstance(this);
        realm.beginTransaction();
        SensorModel realmSensorModel = realm.copyToRealm(sensorModel);
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        realmSensorModel.setRecordDate(todayDate);
        realmSensorModel.setSensorType(mSensor.getType());
        realmSensorModel.setSensorMax1(sensorMax1);
        realmSensorModel.setSensorMax2(sensorMax2);
        realmSensorModel.setSensorMax3(sensorMax3);
        realmSensorModel.setSensorMin1(sensorMin1);
        realmSensorModel.setSensorMin2(sensorMin2);
        realmSensorModel.setSensorMin3(sensorMin3);
        realm.commitTransaction();
        Log.i(Constants.TAG_NAME,"Record stored");

        mSensorManager.unregisterListener(this);
        Log.i(Constants.TAG_NAME,"Unregistered sensor listener");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(realm!=null) {
            realm.close();
            Log.i(Constants.TAG_NAME,"Closed realm db");
        }
    }
}
