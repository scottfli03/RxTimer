package com.rxalarms.rxtimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Dimple Doshi on 6/20/2015.
 * This class will help in render list
 */
public class AlarmListAdapter extends BaseAdapter {

    private List<ModelAlarm> mAlarm;
    private Context context;

    /**
     * Construction with parameter
     * @param con context of current state of application
     * @param alarm alarm list
     */

    public AlarmListAdapter(Context con, List<ModelAlarm> alarm) {
        context = con;
        mAlarm = alarm;
    }

    @Override
    public int getCount() {
        if (mAlarm != null) {
            return mAlarm.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mAlarm != null) {
            return mAlarm.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mAlarm != null) {
            return mAlarm.get(position).getID();
        }
        return 0;
    }

    /**
     * Sets the alarms
     * @param alarms    A list of ModelAlarms
     */
    public void setAlarms(List<ModelAlarm> alarms) {
        this.mAlarm = alarms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = lInflater.inflate(R.layout.activity_reminder_list_item, parent, false);
        }

        ModelAlarm model = (ModelAlarm)getItem(position);

        TextView tvTime = (TextView) convertView.findViewById(R.id.alarm_item_time);
        tvTime.setText(String.format("%02d : %02d", model.getHours(), model.getMinutes()) );

        TextView tvReminderInfo = (TextView)convertView.findViewById(R.id.alarm_item_reminder);
        tvReminderInfo.setText(model.toStringReminderInfo());

        return convertView;
    }
}
