package com.example.mid_year_registration;

import android.app.Activity;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import com.example.mid_year_registration.CoordinatorMenuActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class CoordinatorMenuTest extends ActivityInstrumentationTestCase2 {
    Activity activity;

    public CoordinatorMenuTest(){
       super(CoordinatorMenuActivity.class);}

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){

        onView(withId(R.id.textView4)).check(matches(isDisplayed()));
        onView(withId(R.id.circle_menu2)).check(matches(isDisplayed()));

    }
}
