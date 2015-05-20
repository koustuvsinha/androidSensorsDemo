package com.koustuvsinha.testsensors.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koustuvsinha.testsensors.R;
import com.koustuvsinha.testsensors.models.SensorModel;
import com.koustuvsinha.testsensors.utils.Constants;

import java.util.List;

/**
 * Created by koustuv on 20/5/15.
 */
public class SensorRecordsAdapter extends RecyclerView.Adapter<SensorRecordsAdapter.ViewHolder> {

    private List<SensorModel> recordList;
    private Context mContext;
    private boolean isEmpty;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView recordDateView;
        public TextView dbData1View;
        public TextView dbData2View;
        public TextView dbData3View;
        public ViewHolder(View v,boolean isEmpty) {
            super(v);
            if(!isEmpty) {
                recordDateView = (TextView) v.findViewById(R.id.recordDate);
                dbData1View = (TextView) v.findViewById(R.id.dbData1);
                dbData2View = (TextView) v.findViewById(R.id.dbData2);
                dbData3View = (TextView) v.findViewById(R.id.dbData3);
            }
        }
    }

    public SensorRecordsAdapter(Context context,List<SensorModel> recordList) {
        this.mContext = context;
        this.recordList = recordList;
    }

    @Override
    public SensorRecordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        isEmpty = false;
        if(!recordList.isEmpty()) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.db_list_item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.db_list_item_empty, parent, false);
            isEmpty = true;
        }
        ViewHolder vh = new ViewHolder(v,isEmpty);
        return vh;
    }

    @Override
    public void onBindViewHolder(SensorRecordsAdapter.ViewHolder holder, int position) {
        if(!isEmpty) {
            holder.recordDateView.setText(recordList.get(position).getRecordDate());
            Float max1 = recordList.get(position).getSensorMax1();
            Float max2 = recordList.get(position).getSensorMax2();
            Float max3 = recordList.get(position).getSensorMax3();
            Float min1 = recordList.get(position).getSensorMin1();
            Float min2 = recordList.get(position).getSensorMin2();
            Float min3 = recordList.get(position).getSensorMin3();
            holder.dbData1View.setText(String.format(Constants.FLOAT_LOCALE,max1) + " / " + String.format(Constants.FLOAT_LOCALE,min1));
            holder.dbData2View.setText(String.format(Constants.FLOAT_LOCALE,max2) + " / " + String.format(Constants.FLOAT_LOCALE,min2));
            holder.dbData3View.setText(String.format(Constants.FLOAT_LOCALE,max3) + " / " + String.format(Constants.FLOAT_LOCALE,min3));
        }
    }

    @Override
    public int getItemCount() {
        if(recordList.isEmpty()) {
            return 1;
        } else {
            return recordList.size();
        }
    }
}
