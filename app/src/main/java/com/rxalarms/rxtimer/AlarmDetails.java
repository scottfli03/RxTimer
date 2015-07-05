package com.rxalarms.rxtimer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * @Author Dimple Doshi
 * This class display details of alarm
 * It will give user an option to edit and delete alarm
 */

public class AlarmDetails extends ActionBarActivity {

    private ModelAlarm alarmDetails;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    private int startHour;
    private int startMin;
    private EditText pName;
    private EditText mName;
    private EditText dos;
    private EditText inst;
    private EditText time;

    public AlarmDetails() {}
    /**
     * This method initialize activity
     * @param savedInstanceState Bundle - most recently supplied data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_details);

        long id = getIntent().getExtras().getLong("id");

        if (id < 0) {
            finish();
        }
        else {
            alarmDetails = dbHelper.getAlarm(id);
        }
        populateAlarmDetails();
    }



    /**
     * This method Initialize the contents of the Activity's standard options menu
     * @param menu to place item
     * @return true if menu to be displayed
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_details, menu);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method display PatientName, MedicineNAme, Dosage,
     * Special Instruction and time of selected alarm
     */

    private void populateAlarmDetails() {

        pName = (EditText) findViewById(R.id.alarmDetails_patientName);
        pName.setText(alarmDetails.getPatient());
        pName.setEnabled(false);

        mName = (EditText) findViewById(R.id.alarmsDetails_medName);
        mName.setText(alarmDetails.getMedicine());
        mName.setEnabled(false);

        dos = (EditText) findViewById(R.id.alarmDetails_dosage);
        dos.setText(alarmDetails.getDosage());
        dos.setEnabled(false);

        inst = (EditText) findViewById(R.id.alarmDetails_speInstruction);
        inst.setText(alarmDetails.getInstructions());
        inst.setEnabled(false);

        time = (EditText) findViewById(R.id.alarmDetails_time);
        time.setText(alarmDetails.getHours() + ":" + alarmDetails.getMinutes());
        time.setEnabled(false);

    }


}
