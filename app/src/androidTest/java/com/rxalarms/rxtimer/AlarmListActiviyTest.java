package com.rxalarms.rxtimer;

/**
 * Created by Dimple Doshi
 *
 */

import android.app.Activity;
import android.app.ListActivity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.util.EnumSet.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Created by hardik on 7/19/2015.
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

    @Test
    public void saveListItem() {
        Espresso.onView(ViewMatchers.withId(R.id.action_add_reminder)).perform(ViewActions.click());
    }

    @Test
    public void testListViewClickC() {
        Espresso.onView(withId(android.R.id.list)).perform(click());
    }


}
