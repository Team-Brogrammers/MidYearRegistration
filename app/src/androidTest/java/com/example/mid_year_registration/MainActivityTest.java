package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSubstring;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity activity;
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        // dismiss the progress dialog
        if(activity.getmProgressDialog().isShowing()){
            activity.getmProgressDialog().dismiss();
        }

        //check that the recyclerView is visible
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @SmallTest
    public void testListScroll(){
        // dismiss the progress dialog
        if(activity.getmProgressDialog().isShowing()){
            activity.getmProgressDialog().dismiss();
        }

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        onView(withId(R.id.recyclerView)).perform(swipeDown());
    }

}
