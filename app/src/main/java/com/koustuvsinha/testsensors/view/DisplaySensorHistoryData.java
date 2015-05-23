package com.koustuvsinha.testsensors.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koustuvsinha.testsensors.R;
import com.koustuvsinha.testsensors.adapters.SensorRecordsAdapter;
import com.koustuvsinha.testsensors.models.SensorModel;
import com.koustuvsinha.testsensors.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplaySensorHistoryData.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplaySensorHistoryData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplaySensorHistoryData extends Fragment {

    public static final String PAGE_NAME = "Display Sensor History";
    private int selectedSensor;
    private Realm realm;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<SensorModel> recordData;

    private OnFragmentInteractionListener mListener;

    public static DisplaySensorHistoryData newInstance(int selectedSensor) {
        DisplaySensorHistoryData fragment = new DisplaySensorHistoryData();
        Bundle args = new Bundle();
        args.putInt(Constants.SELECTED_SENSOR,selectedSensor);
        fragment.setArguments(args);
        return fragment;
    }

    public DisplaySensorHistoryData() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null) {
            selectedSensor = savedInstanceState.getInt(Constants.SELECTED_SENSOR);
        } else {
            Bundle args = getArguments();
            selectedSensor = args.getInt(Constants.SELECTED_SENSOR);
        }

        realm = Realm.getInstance(getActivity());
        RealmQuery<SensorModel> query = realm.where(SensorModel.class);
        query.equalTo("sensorType",selectedSensor);
        RealmResults<SensorModel> result = query.findAllSorted("timestamp",RealmResults.SORT_ORDER_DESCENDING);

        if(result.size() > 0) {
           recordData = result.subList(0,result.size());
        } else {
           recordData = new ArrayList<SensorModel>(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display_sensor_history_data, container, false);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recordsView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SensorRecordsAdapter(getActivity(),recordData);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

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

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(Constants.TAG_NAME, PAGE_NAME + ": onDestroy");
        if(realm!=null) {
            realm.close();
            Log.i(Constants.TAG_NAME,"Closed realm db");
        }
    }

}
