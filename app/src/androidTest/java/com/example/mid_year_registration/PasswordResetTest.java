package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PasswordResetTest extends ActivityInstrumentationTestCase2<PasswordResetActivity> {
    PasswordResetActivity activity;

    public PasswordResetTest() {
        super(PasswordResetActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void invalidTest1(){
        onView(withId(R.id.resetPasswordEditText)).perform(typeText("invalidsocialate"), closeSoftKeyboard());
        onView(withId(R.id.resetPasswordButton)).perform(click());
    }

    @SmallTest
    public void invalidInputTest(){
        onView(withId(R.id.resetPasswordEditText)).perform(typeText("invalidsocialate@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetPasswordButton)).perform(click());
    }

}
