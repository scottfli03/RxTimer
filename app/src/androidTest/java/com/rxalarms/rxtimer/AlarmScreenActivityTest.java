package com.rxalarms.rxtimer;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import junit.framework.TestResult;

/**
 * Created by Connor on 7/14/2015.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AlarmScreenActivityTest {
    AlarmScreen screen;

    @Rule
    public ActivityTestRule<AlarmScreen> mActivityRule = new ActivityTestRule<>(
            AlarmScreen.class);
    @Test
    public  void shouldBeClickable(){
        onView(withId(R.id.alarm_screen_button)).check(ViewAssertions.matches(isEnabled()));
    }

    @Test
    public void testNameDisplayed()  {

        onView(withId(R.id.alarm_screen_name)).check(ViewAssertions.matches(isEnabled()));
    }


}
