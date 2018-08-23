package com.example.mid_year_registration;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignUpTesting {
    @Rule
    public ActivityTestRule<SignUpActivity> mainActivityActivityTestRule=
            new ActivityTestRule<>(SignUpActivity.class);
    @Test
    @SmallTest
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.mid_year_registration", appContext.getPackageName());
    }

    @Test
    @MediumTest
    public void clickTextInputLayout(){
        onView(withId(R.id.witsImageView)).check(matches(isDisplayed()));

        onView(withId(R.id.usernameEditText)).perform(click(),typeText("1153631@students.wits.ac.za"), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).check(matches(isDisplayed()));

        onView(withId(R.id.passwordEditText)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));

        onView(withId(R.id.adminCheckBox)).check(matches(isClickable()));
        onView(withId(R.id.adminCheckBox)).perform(click()).check(matches(isChecked()));

        onView(withId(R.id.submitButton)).check(matches(isClickable()));
        onView(withId(R.id.submitButton)).perform(click());

    }

}
