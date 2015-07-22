package com.rxalarms.rxtimer;


import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Dimple Doshi on 7/19/2015.
 * This class test the Alarmlistactivity
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AlarmListActiviyTest extends ActivityInstrumentationTestCase2<AlarmList> {

    private Activity activity;
    private ListView lv;
    private TextView tvInfo;

    @Rule
    public ActivityTestRule<AlarmList> mActivityRule = new ActivityTestRule<>(
            AlarmList.class);

    public AlarmListActiviyTest() {
        super(AlarmList.class);
    }

    /**
     * Test click on add list icon
     */

    @Test
    public void testAddListItem() {
        Espresso.onView(ViewMatchers.withId(R.id.action_add_reminder)).perform(ViewActions.click());
    }

    /**
     * Test click on list
     */
    @Test
    public void testListViewClickC() {
        Espresso.onView(withId(android.R.id.list)).perform(click());
    }


}
