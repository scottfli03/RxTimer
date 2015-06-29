package com.rxalarms.rxtimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.style.TtsSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;


/***
 * @author Tracey Wilson
 *
 * this class sets up a form for the user to input information about the patient, medicine,
 * the alarm frequencey, etc.
 */
public class AlarmCreator extends ActionBarActivity implements View.OnClickListener {
    private EditText alarmTonetxt;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private EditText timeEtxt;
    private int startHour;
    private int startMin;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ModelAlarm alarmDetails;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_creator);

        alarmDetails = new ModelAlarm();
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();


        final EditText ringToneContainer = (EditText) findViewById(R.id.alarm_label_tone_selection);
        ringToneContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent, 1);
            }
        });
    }

//get the fields and input types
    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.startDateSet);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.endDateSet);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        toDateEtxt.requestFocus();

        timeEtxt = (EditText) findViewById(R.id.startTime);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        timeEtxt.requestFocus();
    }

    /***
     * sets up the date and time pickers on the form. When the start date end date or time are selected
     * the appropriate picker will be displayed
     */
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        timeEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        timeEtxt.setText(String.format("%02d : %02d", hour, minute));
        this.startHour = hour;
        this.startMin = minute;
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minuteOfDay) {
                        // Display Selected time in textbox
                        timeEtxt.setText(String.format("%02d : %02d", hourOfDay, minuteOfDay));
                        startHour = hourOfDay;
                        startMin = minuteOfDay;

                    }
                }, hour, minute, false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_creator, menu);
        return super.onCreateOptionsMenu(menu);

    }


    /***
     * identifies which field was selected
     * @param view  The view
     */
    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        } else if(view == timeEtxt) {
            timePickerDialog.show();
        }
    }

    /***
     * gives function to the action bar save button
     * @param item  The MenuItem
     * @return the item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();

                break;
            }

            case R.id.action_save_reminder: {
                if (isValid()) {
                    updateModelFromLayout();


                    AlarmManagerHelper.cancelAlarms(this);

                    if (alarmDetails.getID() < 0) {
                        dbHelper.createAlarm(alarmDetails);
                    } else {
                        dbHelper.updateAlarm(alarmDetails);
                    }
                    AlarmManagerHelper.setAlarms(this);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * this method will get and store the values that the user input when
     * a new alarm is created
     */
    private void updateModelFromLayout() {
        //get the values that the user input
        EditText patient = (EditText) findViewById(R.id.EditTextName);
        alarmDetails.setPatient(patient.getText().toString());

        EditText medicine = (EditText) findViewById(R.id.medNameText);
        alarmDetails.setMedicine(medicine.getText().toString());

        EditText dosage = (EditText) findViewById(R.id.editTextDosage);
        alarmDetails.setDosage(dosage.getText().toString());

        EditText instructions = (EditText) findViewById(R.id.specialInstText);
        alarmDetails.setInstructions(instructions.getText().toString());

        EditText startDate = (EditText) findViewById(R.id.startDateSet);
        //alarmDetails.setStartDate(startDate.getText());

        EditText endDate = (EditText) findViewById(R.id.endDateSet);
        //alarmDetails.setEndDate((Date)endDate.getText());

        //work around to set the hour and minutes as separate fields in the Alarm Model object.
        alarmDetails.setAlarmHour(startHour);
        alarmDetails.setAlarmMinutes(startMin);

        /***
         * gets the value of the Checkbox. If the value is true then the alarm is enabled
         */
        CheckBox alarmOn = (CheckBox) findViewById(R.id.CheckBoxResponse);
        if(alarmOn.isChecked()){
            alarmDetails.setEnabled(true);
        }


    }

    /***
     * picking the alarm sound
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    Uri name = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    alarmDetails.setRingtone(name);

                    TextView txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
                    txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.getRingtone()).getTitle(this));
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }


    /***
     * this method validates the text fields and dates on the user input form
     * @return true if all the data is correct false if one or more of the fields is not
     * correct
     */
    private boolean isValid(){
        EditText patient = (EditText) findViewById(R.id.EditTextName);
        EditText medicine = (EditText) findViewById(R.id.medNameText);
        EditText dosage = (EditText) findViewById(R.id.editTextDosage);
        EditText instructions = (EditText) findViewById(R.id.specialInstText);
        EditText startDate = (EditText) findViewById(R.id.startDateSet);
        EditText endDate = (EditText) findViewById(R.id.endDateSet);

        if( patient.getText().toString().length() == 0 ){
            patient.setError( "This field is required!" );
            return false;
        } else if( medicine.getText().toString().length() == 0 ) {
            medicine.setError("This field is required!");
            return false;
        } else if( dosage.getText().toString().length() == 0 ) {
            dosage.setError("This field is required!");
            return false;
        } else if( instructions.getText().toString().length() == 0 ) {
            instructions.setError( "This field is required!" );
            return false;
        }


        Date current = new Date();
        if (new Date().after((Date)startDate.getText())) {
           return  false;
        } else if (((Date) startDate.getText()).after((Date)endDate.getText())) {
            return false;
        }


        return true;
    }

}