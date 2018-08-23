package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    LoginActivity activity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.appTitleTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).check(matches(isClickable()));
        onView(withId(R.id.resetPasswordTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.creatAccountTextView)).check(matches(isDisplayed()));

    }

    @SmallTest
    public void testUserInput(){
        onView(withId(R.id.emailEditText)).perform(typeText("invalidsocialate@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("furry123"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
    }

}