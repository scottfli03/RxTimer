package com.rxalarms.rxtimer;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Tracey on 6/20/2015.
 */
public class AlarmManagerHelper extends BroadcastReceiver {

    public static final String ID = "id";
    public static final String NAME = "name";
   // public static final String MEDICINE = "medicine";
    public static final String TIME_HOUR = "timeHour";
    public static final String TIME_MINUTE = "timeMinute";
    public static final String TONE = "alarmTone";

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    /***
     * Sets the time for the alarm and checks to make sure the alarm is in the future
     * if it is then it creates an alarm instance
     * @param context
     */
    public static void setAlarms(Context context) {
        cancelAlarms(context);

        AlarmDBHelper dbHelper = new AlarmDBHelper(context);

        List<ModelAlarm> alarms =  dbHelper.getAlarms();

        if (alarms != null) {
            for (ModelAlarm alarm : alarms) {
                if (alarm.getIsEnabled()) {
                    Calendar calendar = Calendar.getInstance();
                    //final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.getHours());
                    calendar.set(Calendar.MINUTE, alarm.getMinutes());
                    calendar.set(Calendar.SECOND, 00);

                    PendingIntent pIntent = createPendingIntent(context, alarm);
                    if (!(alarm.getHours() < nowHour) && !(alarm.getHours() == nowHour && alarm.getMinutes() <= nowMinute)) {

                        setAlarm(context, calendar, pIntent);

                    }
                }
            }
        }
    }


    /***
     * Method to handle new API changes for setting up the alarm
     * @param context the current context
     * @param calendar the current  alarm time
     * @param pIntent the pending intent for this alarm
     */
    @SuppressLint("NewApi")
    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }

    }


    /***
     * Cancels alarms no longer in use
     * @param context
     */
    public static void cancelAlarms(Context context) {
        AlarmDBHelper dbHelper = new AlarmDBHelper(context);

        List<ModelAlarm> alarms =  dbHelper.getAlarms();

        if (alarms != null) {
            for (ModelAlarm alarm : alarms) {
                if (alarm.getIsEnabled()) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }
    /***
     * Cancels  an alarm no longer in use
     * @param context
     */
    public static void cancelAlarm(Context context, long alarmID) {
        AlarmDBHelper dbHelper = new AlarmDBHelper(context);

        ModelAlarm alarm =  dbHelper.getAlarm(alarmID);

        if (alarm.getIsEnabled()) {
            PendingIntent pIntent = createPendingIntent(context, alarm);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pIntent);
        }

    }
    /*
    this is the code that allows the alarms to work when the app is not active
     */
    private static PendingIntent createPendingIntent(Context context, ModelAlarm model) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, model.getID());
        intent.putExtra(NAME, model.toStringReminderInfo());
        intent.putExtra(TIME_HOUR, model.getHours());
        intent.putExtra(TIME_MINUTE, model.getMinutes());
        intent.putExtra(TONE, model.getRingtone().toString());

        return PendingIntent.getService(context, (int) model.getID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }




//code from failed attempt not sure if we may need later will remove in interation 3 if not needed

    /***
     * for testing only to be removed from final version
     * 4 repeat method
     * this method will allow the alarm to repeat every 4 hours
     *
     */


 /*   private static void repeat4(ModelAlarm alarm, Calendar calendar, Context context){

        long time = calendar.getTimeInMillis();
        long repeatTime =  4*60*1000; // minutes right now for easier testing

        AlarmManager mgrAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, alarm.getID());
        intent.putExtra(NAME, alarm.toStringReminderInfo());
        intent.putExtra(TIME_HOUR, alarm.getHours());
        intent.putExtra(TIME_MINUTE, alarm.getMinutes());
        intent.putExtra(TONE, alarm.getRingtone().toString());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mgrAlarm.setRepeating(AlarmManager.RTC_WAKEUP, time , repeatTime, pendingIntent);

        }

    *//***
     * 8 repeat method
     * this will let the alarm repeat every 8 hours
     *
     *//*

    private static void repeat8(ModelAlarm alarm, Calendar calendar, Context context){

        long time = calendar.getTimeInMillis();
        long repeatTime =  8*60*1000; // minutes right now for easier testing

        AlarmManager mgrAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, alarm.getID());
        intent.putExtra(NAME, alarm.toStringReminderInfo());
        intent.putExtra(TIME_HOUR, alarm.getHours());
        intent.putExtra(TIME_MINUTE, alarm.getMinutes());
        intent.putExtra(TONE, alarm.getRingtone().toString());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mgrAlarm.setRepeating(AlarmManager.RTC_WAKEUP, time, repeatTime, pendingIntent);

    }

    *//***
     *12repeat method
     *this will let the alarm repeat every 12 hours
     *
     *//*

    private static void repeat12(ModelAlarm alarm, Calendar calendar, Context context){

        long time = calendar.getTimeInMillis();
        long repeatTime =  12*60*1000; // minutes right now for easier testing

        AlarmManager mgrAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, alarm.getID());
        intent.putExtra(NAME, alarm.toStringReminderInfo());
        intent.putExtra(TIME_HOUR, alarm.getHours());
        intent.putExtra(TIME_MINUTE, alarm.getMinutes());

        intent.putExtra(TONE, alarm.getRingtone().toString());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mgrAlarm.setRepeating(AlarmManager.RTC_WAKEUP, time , repeatTime, pendingIntent);

    }


    *//***
     *24 repeat method
     *this will let the alarm repeat every 24 hours
     *
     *//*
    private static void repeat24(ModelAlarm alarm, Calendar calendar, Context context){

        long time = calendar.getTimeInMillis();
        long repeatTime =  24*60*1000; // minutes for testing

        AlarmManager mgrAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, alarm.getID());
        intent.putExtra(NAME, alarm.toStringReminderInfo());
        intent.putExtra(TIME_HOUR, alarm.getHours());
        intent.putExtra(TIME_MINUTE, alarm.getMinutes());
        intent.putExtra(TONE, alarm.getRingtone().toString());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mgrAlarm.setRepeating(AlarmManager.RTC_WAKEUP, time, repeatTime, pendingIntent);

    }
*/

}
