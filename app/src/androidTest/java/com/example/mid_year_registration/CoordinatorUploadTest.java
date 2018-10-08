package com.example.mid_year_registration;

import android.app.Activity;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import com.example.mid_year_registration.CoordinatorMenuActivity;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class CoordinatorUploadTest extends ActivityInstrumentationTestCase2<CoordinatorUploadActivity> {
    CoordinatorUploadActivity activity;

    public CoordinatorUploadTest(){
        super(CoordinatorUploadActivity.class);}

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @Test
    @SmallTest
    public void testStudentNumberValidation(){
        assertEquals(activity.isValidStudentNo("1234567"),true);
        assertEquals(activity.isValidStudentNo("1234"),false);
        assertEquals(activity.isValidStudentNo(null), false);
    }

    @Test
    @SmallTest
    public void testCourseCodeValidation(){
        assertEquals(CoordinatorUploadActivity.checkString("COMS1011"), true);
        assertEquals(CoordinatorUploadActivity.checkString("Coms1011"), false);
    }


}
