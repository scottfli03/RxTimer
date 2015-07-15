package com.rxalarms.rxtimer.Tests;

import android.test.InstrumentationTestCase;
import android.widget.EditText;

import com.rxalarms.rxtimer.AlarmCreator;
import com.rxalarms.rxtimer.ModelAlarm;
import com.rxalarms.rxtimer.R;

/**
 * Created by Tracey on 6/18/2015.
 * use http://rexstjohn.com/unit-testing-with-android-studio/ to set up testing.
 *
 */

public class AlarmInfoTest extends InstrumentationTestCase {

    public void testUpdateModel() throws Exception {
        ModelAlarm alarmDetails = new ModelAlarm();
        ModelAlarm alarm = setModel();
        AlarmCreator AC = new AlarmCreator();
       // AC.updateModelFromLayout();




        assertEquals(alarm.toString(), alarmDetails.toString());
    }


    public void testSetTextField(){

    }


    /**
     * Creates a template for the ModelAlarm.
     * @return  The ModelAlarm
     */
    private ModelAlarm setModel() {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setPatient("Dude");
        alarm.setMedicine("Lovin");
        alarm.setDosage("1 Dose");
        alarm.setInstructions("With 2 drinks");
        alarm.setAlarmHour(12);
        alarm.setAlarmMinutes(34);
        alarm.setEnabled(true);
        return alarm;
    }


}

