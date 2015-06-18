package com.rxalarms.rxtimer;

/**
 * Created by Scott Flischel on 6/17/15.
 *
 * ModelAlarm holds information about the Alarm and it's reminder information
 */
public class ModelAlarm {

    private String patient;
    private String medicine;
    private String dosage;
    private String instructions;
    private long id;
    private int alarmHour;
    private int alarmMinutes;
    private boolean isEnabled;


    /**
     * ModelAlarm Constructor that initializes it's instance variables.
     */
    public ModelAlarm() {
        this.patient = "";
        this.medicine = "";
        this.dosage = "";
        this.instructions = "";
        this.alarmHour = 24;
        this.alarmMinutes = 0;
        this.isEnabled = false;
    }

    /**
     * Sets the patient's name.
     * @param patient   The patient's name
     */
    public void setPatient(String patient) {
        this.patient = patient;
    }

    /**
     * Sets the medicine's name.
     * @param medicine  The medicine's name
     */
    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    /**
     * Sets the dosage amount.
     * @param dosage    The dosage amount
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Sets the special instructions.
     * @param instructions  The special instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Sets the alarm's hour 1-24.
     * @param alarmHour The alarms hour
     */
    public void setAlarmHour(int alarmHour) {
        if (alarmHour < 1 || alarmHour > 24) {
            throw new IllegalArgumentException("The Hour must be between 1 and 24");
        }
        this.alarmHour = alarmHour;
    }

    /**
     * Sets the alarm's minutes 0-59.
     * @param alarmMinutes the alarms minutes
     */
    public void setAlarmMinutes(int alarmMinutes) {
        if (alarmMinutes < 0 || alarmMinutes > 59) {
            throw new IllegalArgumentException("The minutes must be between 0 and 59");
        }
        this.alarmMinutes = alarmMinutes;
    }

    /**
     * Set's if the alarm is enabled.
     * @param isEnabled is the alarm enabled
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Returns a String representation of the Reminder information.
     * @return  The String representation of the reminder info
     */
    public String toStringReminderInfo() {
        return this.patient + " needs to take " + this.dosage + " of " + this.medicine
                + "\nInstructions: " + this.instructions;
    }

    /**
     * Returns a String representation of the alarm info
     * @return String representation of the alarm info
     */
    public String toStringAlarmInfo() {
        return alarmHour + ":" + alarmMinutes + " " + isEnabled;
    }

    /**
     * Returns a String representation of the ModelAlarm Object
     * @return String representation of the ModelAlarm Object
     */
    @Override
    public  String toString() {
        return toStringAlarmInfo() + " " + toStringReminderInfo();
    }
}
