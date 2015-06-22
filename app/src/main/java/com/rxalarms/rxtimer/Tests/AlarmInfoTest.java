package com.rxalarms.rxtimer.Tests;

import android.test.InstrumentationTestCase;

import com.rxalarms.rxtimer.ModelAlarm;

/**
 * Created by Tracey on 6/18/2015.
 * use http://rexstjohn.com/unit-testing-with-android-studio/ to set up testing.
 *
 */

public class AlarmInfoTest extends InstrumentationTestCase {
    public void testUpdateModel() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
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

