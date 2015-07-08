package com.rxalarms.rxtimer;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * @Author Dimple Doshi
 * This class display details of alarm
 * It will give user an option to edit and delete alarm
 */

public class AlarmDetails extends ActionBarActivity {

    private ModelAlarm alarmDetails;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    private int hr;
    private int min;
    private EditText pName;
    private EditText mName;
    private EditText dos;
    private EditText inst;
    private EditText time;
    private long id;
    private TimePickerDialog timePickerDialog;

    public AlarmDetails() {}
    /**
     * This method initialize activity
     * @param savedInstanceState Bundle - most recently supplied data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_details);

        id = getIntent().getExtras().getLong("id");

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

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.action_edit_reminder: {
                this.makeEditingEnable();
                this.editTime();
                break;

            }
            case R.id.action_save_update_reminder: {
                if ( verifyRequiredFiled()) {
                    updatedAlarmDetails();
                    dbHelper = new AlarmDBHelper(this);
                    dbHelper.updateAlarm(alarmDetails);
                    setResult(RESULT_OK);
                    finish();
                }
                break;

            }
            case R.id.action_delete_reminder: {
                deleteAlarm(id);
                break;

            }

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
        time.requestFocus();
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == time) {
                    timePickerDialog.show();
                }
            }
        });

    }

    /**
     * This method will update alarm details after changes made to alarm
     */
    private void updatedAlarmDetails() {
        pName= (EditText) findViewById(R.id.alarmDetails_patientName);
        alarmDetails.setPatient(pName.getText().toString());

        mName = (EditText) findViewById(R.id.alarmsDetails_medName);
        alarmDetails.setMedicine(mName.getText().toString());

        dos = (EditText) findViewById(R.id.alarmDetails_dosage);
        alarmDetails.setDosage(dos.getText().toString());

        inst = (EditText) findViewById(R.id.alarmDetails_speInstruction);
        alarmDetails.setInstructions(inst.getText().toString());

        time = (EditText) findViewById(R.id.alarmDetails_time);
        alarmDetails.setAlarmHour(hr);
        alarmDetails.setAlarmMinutes(min);
        onBackPressed();
    }



    /**
     * This method will make alarmDetails
     * editText enable to modify
     */

    private void makeEditingEnable() {

        pName.setEnabled(true);
        mName.setEnabled(true);
        dos.setEnabled(true);
        inst.setEnabled(true);
        time.setEnabled(true);
    }

    /**
     * This method will delete alarm
     * @param id id of the selected alarm
     */
    private void deleteAlarm(long id) {
        final long alarmId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please confirm").setTitle("Delete Alarm ?")
                .setCancelable(true).setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteAlarm(alarmId);
                        onBackPressed();
                        Toast toast = Toast.makeText(getApplicationContext(), " Alarm has been Deleted", Toast.LENGTH_SHORT);
                        toast.show();
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);

                    }
                }).show();

    }


    /**
     * This method will set timepicker with given
     * alarm time
     *
     */
    private void editTime() {
        int hrs = alarmDetails.getHours();
        int mins = alarmDetails.getMinutes();

        this.hr = hrs;
        this.min = mins;
        this.timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        time.setText(String.format("%02d : %02d", hour, minute));
                        hr = hour;
                        min = minute;

                    }
                }, hr, mins, false);

    }

    /**
     * This method validate that filed are not empty before
     * submitting updates
     * make user to input values for all field
     * @return true if all required filed present
     */

    private boolean verifyRequiredFiled() {
        EditText patient = (EditText) findViewById(R.id.alarmDetails_patientName);
        EditText medicine = (EditText) findViewById(R.id.alarmsDetails_medName);
        EditText dosage = (EditText) findViewById(R.id.alarmDetails_dosage);
        EditText time = (EditText) findViewById(R.id.alarmDetails_time);
        EditText specialInstruction = (EditText) findViewById(R.id.alarmDetails_speInstruction);

        if( patient.getText().toString().length() == 0 ){
            patient.setError( "This field is required!" );
            return false;
        } else if( medicine.getText().toString().length() == 0 ) {
            medicine.setError("This field is required!");
            return false;
        } else if( dosage.getText().toString().length() == 0 ) {
            dosage.setError("This field is required!");
            return false;
        } else if( specialInstruction.getText().toString().length() == 0 ) {
            specialInstruction.setError("This field is required!");
            return false;
        } else if (time.getText().toString().length()==0) {
            time.setError("This field is required!");
            return false;

        }



        return true;

    }

}
