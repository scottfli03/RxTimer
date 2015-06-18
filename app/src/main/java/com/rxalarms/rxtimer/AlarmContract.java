package com.rxalarms.rxtimer;

import android.provider.BaseColumns;

/**
 * Created by Scott Flischel on 6/17/15.
 * Establishes a contract for the Database Build.
 */
public final class AlarmContract {

    public AlarmContract() {}

    /**
     * Defines what the alarm table's name and attributes should be
     */
    public abstract static class Alarm implements BaseColumns {
        public final static String TABLE_NAME = "alarm";
        public final static String COLUMN_NAME_PATIENT_NAME = "patient";
        public final static String COLUMN_NAME_MEDICINE_NAME = "medicine";
        public final static String COLUMN_NAME_DOSAGE = "dosage";
        public final static String COLUMN_NAME_INSTRUCTIONS = "instructions";
        public final static String COLUMN_NAME_HOUR = "hour";
        public final static String COLUMN_NAME_MINUTES = "minutes";
        public final static String COLUMN_NAME_ISENABLED = "isEnabled";
    }
}
