package com.rxalarms.rxtimer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 *    This class has list of reminder and contain option to add new reminder
 *    Created by Dimple Doshi.
 */


public class AlarmList extends ActionBarActivity {
    private AlarmListAdapter mAdapter;
    private AlarmDBHelper helper = new AlarmDBHelper(this);
    public final static int SAVED = 1;
    private Context mContext;

    /**
     * This method initialize activity
     * @param savedInstanceState Bundle - most recently supplied data
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        mContext = this;
        ListView lv = (ListView) findViewById(android.R.id.list);

        //Set empty listView with message
        View empty = findViewById(R.id.empty);
        lv.setEmptyView(empty);

        mAdapter = new AlarmListAdapter(mContext, helper.getAlarms());
        lv.setAdapter(mAdapter);
    }

    /**
     * This method Initialize the contents of the Activity's standard options menu
     * @param menu to place item
     * @return true if menu to be displayed
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        return true;
    }

    /**
     * This method is call when Item from the menu is selected
     * @param item menu item that was selected
     * @return true
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_add_reminder: {
                Intent intent = new Intent(this, AlarmCreator.class);
                startActivityForResult(intent, SAVED);
                break;
            }

            case R.id.action_settings:
                return true;
        }

        return true;
    }

    /**
     *  This method called when an activity you launched exits,
     *  giving you the requestCode you started it with, the resultCode it returned.
     *
     * @param requestCode integer request code originally supplied to
     * @param resultCode  integer result code returned by
     * @param data intent which return result data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SAVED) {
            List<ModelAlarm> alarms = helper.getAlarms();
            mAdapter.setAlarms(alarms);
            mAdapter.notifyDataSetChanged();
        }
        else {
            List<ModelAlarm> alarms = helper.getAlarms();
            mAdapter.setAlarms(alarms);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * This method will start new activity
     * on item click on listview
     * @param id id of the selected alarm
     */
    public void startAlarmDetailsActivity(long id) {
        Intent intent = new Intent(this, AlarmDetails.class);
        intent.putExtra("id", id);
        ((AlarmList) mContext).startActivityForResult(intent, 0);
    }
}
