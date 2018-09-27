package com.example.mid_year_registration;

import android.app.Activity;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import com.example.mid_year_registration.CoordinatorMenuActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class CoordinatorUploadTest extends ActivityInstrumentationTestCase2<CoordinatorUploadActivity> {
    Activity activity;

    public CoordinatorUploadTest(){
        super(CoordinatorUploadActivity.class);}

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }


}