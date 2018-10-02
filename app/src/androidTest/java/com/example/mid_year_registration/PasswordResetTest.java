package com.example.mid_year_registration;

import android.support.test.espresso.ViewAssertion;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PasswordResetTest {
    @Rule
    public ActivityTestRule<PasswordResetActivity> mActivityRule = new ActivityTestRule<>(
            PasswordResetActivity.class);

    @Test
    @SmallTest
    public void validInputTest() throws InterruptedException {
        onView(withId(R.id.resetPasswordEditText)).perform(typeText("musa950820@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetPasswordButton)).perform(click());
        Thread.sleep(5000);
    }

    @Test
    @SmallTest
    public void visibleTest(){
        onView(withId(R.id.resetPasswordEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPasswordButton)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPasswordButton)).check(matches(isClickable()));
    }


    @Test
    @SmallTest
    public void testInvalidEmail(){
        onView(withId(R.id.resetPasswordEditText)).perform(typeText("12345@students"), closeSoftKeyboard());
        onView(withId(R.id.resetPasswordButton)).perform(click());
    }

    @Test
    @SmallTest
    public void testNoEmail(){
        onView(withId(R.id.resetPasswordEditText)).perform(typeText("123456@students.wits.ac.za"), closeSoftKeyboard());
        onView(withId(R.id.resetPasswordButton)).perform(click());
    }

//    @Test
//    @SmallTest
//    public void testNoEmail1(){
//        onView(withId(R.id.resetPasswordEditText)).perform(typeText("   "), closeSoftKeyboard());
//        onView(withId(R.id.resetPasswordButton)).perform(click());
//    }


}
