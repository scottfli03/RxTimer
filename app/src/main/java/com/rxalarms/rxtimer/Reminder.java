package com.rxalarms.rxtimer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import java.util.List;

/**
 *  Created by Dimple Doshi.
 *  This class has list of reminder and contain option to add new reminder
 */


public class Reminder extends ActionBarActivity {
    private AlarmListAdapter mAdapter;
    private AlarmDBHelper helper = new AlarmDBHelper(this);
    public final static int SAVED = 1;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mContext = this;
        ListView lv = (ListView) findViewById(android.R.id.list);
        mAdapter = new AlarmListAdapter(mContext, helper.getAlarms());
        lv.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_add_reminder: {
                Intent intent = new Intent(this, alarmInfo.class);
                startActivityForResult(intent, SAVED);
                break;
            }

            case R.id.action_settings:
                return true;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SAVED) {
            List<ModelAlarm> alarms = helper.getAlarms();
            mAdapter.setAlarms(alarms);
            mAdapter.notifyDataSetChanged();
        }
    }
}
