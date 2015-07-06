package com.rxalarms.rxtimer;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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

        for (ModelAlarm alarm : alarms) {
            if (alarm.getIsEnabled()) {

                PendingIntent pIntent = createPendingIntent(context, alarm);

                Calendar calendar = Calendar.getInstance();


                //final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);


                calendar.set(Calendar.HOUR_OF_DAY, alarm.getHours());
                calendar.set(Calendar.MINUTE, alarm.getMinutes());
                calendar.set(Calendar.SECOND, 00);
                switch (alarm.getRepeat()) {
                    case 4: {
                        if (!(alarm.getHours() < nowHour) && !(alarm.getHours() == nowHour && alarm.getMinutes() <= nowMinute)) {
                            setAlarm4(context, calendar, pIntent);
                            break;
                        }
                    }
                    case 8: {
                        if (!(alarm.getHours() < nowHour) && !(alarm.getHours() == nowHour && alarm.getMinutes() <= nowMinute)) {
                            setAlarm8(context, calendar, pIntent);
                            break;
                        }
                    }
                    case 12: {
                        if (!(alarm.getHours() < nowHour) && !(alarm.getHours() == nowHour && alarm.getMinutes() <= nowMinute)) {
                            setAlarm12(context, calendar, pIntent);
                            break;
                        }
                    }
                    case 24: {
                        if (!(alarm.getHours() < nowHour) && !(alarm.getHours() == nowHour && alarm.getMinutes() <= nowMinute)) {
                            setAlarm(context, calendar, pIntent);
                            break;
                        }
                    }
                }
               /* //makes sure the time is not already past
                if (!(alarm.getHours() < nowHour) && !(alarm.getHours() == nowHour && alarm.getMinutes() <= nowMinute)) {
                    setAlarm4(context, calendar, pIntent);
                }*/
            }
        }
    }

    /***
     * Method to handle new API changes for setting up the alarm
     * @param context the current context
     * @param calendar the current calendar
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

    //for every 8 hours
    @SuppressLint("NewApi")
    private static void setAlarm8(Context context, Calendar calendar, PendingIntent pIntent) {
        long time = calendar.getTimeInMillis();
        long repeat8 =  8*60*1000; //8 minutes for testing

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeat8, pIntent);

        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
            alarmManager1.set(AlarmManager.RTC_WAKEUP, repeat8, pIntent);
        }
    }

    //for every 4 hours
    @SuppressLint("NewApi")
    private static void setAlarm4(Context context, Calendar calendar, PendingIntent pIntent) {
        long time = calendar.getTimeInMillis();
        long repeat4 =  4*60*1000; //4 mins for testing

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeat4, pIntent);

        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
            alarmManager1.set(AlarmManager.RTC_WAKEUP, repeat4, pIntent);
        }
    }
//for every 12 hours
   // @SuppressLint("NewApi")
    private static void setAlarm12(Context context, Calendar calendar, PendingIntent pIntent) {
        long test = calendar.getTimeInMillis();

        long repeat12 =  12*60*1000 ;//12 minutes right now

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeat12, pIntent);
            //alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, repeat12, AlarmManager.INTERVAL_DAY, pIntent);

        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
            alarmManager1.set(AlarmManager.RTC_WAKEUP, repeat12, pIntent);
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

    /*
    this is the code that allows the alarms to work when the app is not active
     */
    private static PendingIntent createPendingIntent(Context context, ModelAlarm model) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, model.getID());
        intent.putExtra(NAME, model.toStringReminderInfo());
        //intent.putExtra(MEDICINE, model.getMedicine());
        intent.putExtra(TIME_HOUR, model.getHours());
        intent.putExtra(TIME_MINUTE, model.getMinutes());
        intent.putExtra(TONE, model.getRingtone().toString());

        return PendingIntent.getService(context, (int) model.getID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
