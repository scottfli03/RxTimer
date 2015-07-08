package com.rxalarms.rxtimer;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

/***
 * @author  Tracey Wilson 6/20/2015
 * this activity sets up the screen that is displayed when the alarm goes off
 *  and the action of the Dismiss button
 */

public class AlarmScreen extends Activity {

    public final String TAG = this.getClass().getSimpleName();

    private PowerManager.WakeLock mWakeLock;
    private MediaPlayer mPlayer;

    private static final int WAKELOCK_TIMEOUT = 60 * 1000;

    /***
     * sets up the view and fills in the information to be displayed
     * @param savedInstanceState the current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup layout
        this.setContentView(R.layout.activity_alarm_screen);
        final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
        String name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
        String tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);

        TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
        tvName.setText(name);

        TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
        if (nowHour < 12) {
            tvTime.setText(String.format("%02d:%02d", nowHour, nowMinute) + " am");
        } else if (nowHour == 12) {
            tvTime.setText(String.format("%02d:%02d", nowHour, nowMinute) + " pm");
        } else {
            tvTime.setText(String.format("%02d:%02d", nowHour - 12, nowMinute) + " pm");
        }
        Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPlayer.stop();

                finish();
            }
        });

        //Play alarm tone
        mPlayer = new MediaPlayer();
        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    mPlayer.setDataSource(this, toneUri);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mPlayer.setLooping(true);
                    mPlayer.prepare();
                    mPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Ensure wakelock release
        Runnable releaseWakelock = new Runnable() {

            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                }
            }
        };

        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);
    }

    /***
     * this methods are used to wake the phone if it is locked or asleep
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

        // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // Acquire wakelock
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
        }

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
            Log.i(TAG, "Wakelock aquired!!");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }
}
