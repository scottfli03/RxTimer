package com.rxalarms.rxtimer;

/**
 * Created by Tracey Wilson  on 7/15/2015.
 */

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AlarmCreatorActivityTest {


    private static final String STRING_TO_BE_TYPED = "Peter";
    private static final String STRING_FOR_TIME = "7:02";
    private static final String STRING_FOR_REPEAT = "No Repeat";



    @Rule
    public ActivityTestRule<AlarmCreator> mActivityRule = new ActivityTestRule<>(
            AlarmCreator.class);

    @Test
    public void testSetName() {
        onView(withId(R.id.EditTextName)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard()); //line 1

        onView(withId(R.id.EditTextName)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void testSetDosage() {
        onView(withId(R.id.editTextDosage)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard()); //line 1

        onView(withId(R.id.editTextDosage)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void testSetMedicine() {
        onView(withId(R.id.medNameText)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard()); //line 1

        onView(withId(R.id.medNameText)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void testSetInstructions() {
        onView(withId(R.id.specialInstText)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard()); //line 1

        onView(withId(R.id.specialInstText)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void testSetRepeat() {
        onData(withText("No Repeat")).inAdapterView(isDisplayed());

        onData(withText("No Repeat")).inAdapterView(withText(STRING_FOR_REPEAT));
    }

    @Test

    public void testSetTone() {
        onData(withId(R.id.alarm_label_tone_selection)).inAdapterView(isDisplayed());
        String tone ="Select an alarm tone";
        onView(withId(R.id.alarm_label_tone_selection)).check(matches(withHint(tone)));
    }

    @Test
    public void testSetAlarmOn() {
        onView(withId(R.id.CheckBoxResponse)).check(matches(isChecked())); //line 1

    }

    @Test
    public void testSetTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        String time;
        if (hour == 0) {
            time = (String.format("%02d:%02d", hour + 12, minute) + " am");
        } else if (hour < 10) {
            time = (String.format("%01d:%02d", hour, minute) + " am");
        } else if (hour < 12) {
            time = (String.format("%02d:%02d", hour, minute) + " am");
        } else if (hour == 12) {
            time = (String.format("%02d:%02d", hour, minute) + " pm");
        } else if (hour > 12 && hour < 22) {
            time = (String.format("%01d:%02d", hour - 12, minute) + " pm");
        } else {
            time = (String.format("%02d:%02d", hour - 12, minute) + " pm");
        }
        onData(withId(R.id.startTime)).inAdapterView(isDisplayed());

        onView(withId(R.id.startTime)).check(matches(withText(time)));

    }



}
