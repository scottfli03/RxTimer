package com.rxalarms.rxtimer;

/**
 * Created by Connor on 7/15/2015.
 */
import android.support.test.InstrumentationRegistry;
        import android.support.test.espresso.action.ViewActions;
        import android.support.test.rule.ActivityTestRule;
        import android.support.test.runner.AndroidJUnit4;
        import android.test.ActivityInstrumentationTestCase2;
        import android.test.suitebuilder.annotation.LargeTest;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.junit.After;
        import org.junit.Before;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;

        import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.action.ViewActions.click;
        import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
        import static android.support.test.espresso.action.ViewActions.typeText;
        import static android.support.test.espresso.assertion.ViewAssertions.matches;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
        import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AlarmCreatorActivityTest {


    private static final String STRING_TO_BE_TYPED = "Peter";
   /* patient = (EditText) findViewById(R.id.EditTextName);
    medicine = (EditText) findViewById(R.id.medNameText);
    dosage = (EditText) findViewById(R.id.editTextDosage);
    instructions = (EditText) findViewById(R.id.specialInstText);
    alarmOn = (CheckBox) findViewById(R.id.CheckBoxResponse);
    repeatTime = (Spinner) findViewById(R.id.SpinnerFeedbackType);*/

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


}
