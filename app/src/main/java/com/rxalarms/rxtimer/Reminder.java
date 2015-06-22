package com.rxalarms.rxtimer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 *  Created by Dimple Doshi.
 *  This class has list of reminder and contain option to add new reminder
 */


public class Reminder extends ActionBarActivity {

    private AlarmListAdapter adapter;
    private AlarmDBHelper dbHelper;
    private ListView lView;
    private ModelAlarm model;
    private Context context;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        //lView = (ListView) findViewById(R.id.listView);
        //adapter = new AlarmListAdapter(this,dbHelper.getAlarms());
        //lView.setAdapter(adapter);
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
                startActivity(intent);
                break;
            }

            case R.id.action_settings:
                return true;
        }

        return true;
    }
}
