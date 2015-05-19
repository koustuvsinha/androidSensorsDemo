package com.koustuvsinha.testsensors.view;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koustuvsinha.testsensors.R;
import com.koustuvsinha.testsensors.models.SensorModel;
import com.koustuvsinha.testsensors.sensors.SensorManagement;
import com.koustuvsinha.testsensors.utils.Constants;
import com.koustuvsinha.testsensors.utils.DBUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayRawSensorData.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayRawSensorData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayRawSensorData extends Fragment implements SensorEventListener {

    public static final String PAGE_NAME = "Sensor Data Details";

    private OnFragmentInteractionListener mListener;

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
    private int selectedSensor;

    public static DisplayRawSensorData newInstance(int selectedSensor) {
        DisplayRawSensorData fragment = new DisplayRawSensorData();
        Bundle args = new Bundle();
        args.putInt(Constants.SELECTED_SENSOR,selectedSensor);
        fragment.setArguments(args);
        return fragment;
    }

    public DisplayRawSensorData() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getInstance(getActivity());
        SensorManagement sensorManagement = new SensorManagement(getActivity());
        if(savedInstanceState!=null) {
            selectedSensor = savedInstanceState.getInt(Constants.SELECTED_SENSOR);
        } else {
            Bundle args = getArguments();
            selectedSensor = args.getInt(Constants.SELECTED_SENSOR);
        }
        mSensor =  sensorManagement.getSensorByType(selectedSensor);
        mSensorManager = sensorManagement.getSensorManager();

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        RealmQuery<SensorModel> query = realm.where(SensorModel.class);
        // convert today's date in String
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        query.equalTo("recordDate",todayDate);
        query.equalTo("sensorType",mSensor.getType());
        RealmResults<SensorModel> result = query.findAll();
        if(result.size() > 0) {
            sensorModel =  result.get(0);

            Log.i(Constants.TAG_NAME, "Record found, loading the record");

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
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putInt(Constants.SELECTED_SENSOR, selectedSensor);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display_raw_sensor_data, container, false);
        //populate the view here

        TextView detailsTextView = (TextView)v.findViewById(R.id.sensorDetailsName);
        detailsTextView.setText(mSensor.getName());
        TextView detailsVendorView = (TextView)v.findViewById(R.id.sensorVendor);
        detailsVendorView.setText(mSensor.getVendor());
        //detailsDataValuesView = (TextView)findViewById(R.id.sensorDetailsValues);
        detailsData1View = (TextView)v.findViewById(R.id.sensorDetailsData1);
        detailsData2View = (TextView)v.findViewById(R.id.sensorDetailsData2);
        detailsData3View = (TextView)v.findViewById(R.id.sensorDetailsData3);
        //detailsDataView.setText('0');
        detailsData1MaxView = (TextView)v.findViewById(R.id.maxData1);
        detailsData2MaxView = (TextView)v.findViewById(R.id.maxData2);
        detailsData3MaxView = (TextView)v.findViewById(R.id.maxData3);

        detailsData1MinView = (TextView)v.findViewById(R.id.minData1);
        detailsData2MinView = (TextView)v.findViewById(R.id.minData2);
        detailsData3MinView = (TextView)v.findViewById(R.id.minData3);

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
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
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public String getName() {
        return PAGE_NAME;
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.d(Constants.TAG_NAME, PAGE_NAME + ": onDestroyView");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(Constants.TAG_NAME, PAGE_NAME + ": onDestroy");
        // unregister listener
        realm = Realm.getInstance(getActivity());
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

        if(realm!=null) {
            realm.close();
            Log.i(Constants.TAG_NAME,"Closed realm db");
        }
    }

}
