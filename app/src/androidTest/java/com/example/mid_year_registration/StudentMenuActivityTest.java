package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class StudentMenuActivityTest extends ActivityInstrumentationTestCase2<StudentMenuActivity> {
    StudentMenuActivity activity;

    public StudentMenuActivityTest(){
        super(StudentMenuActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.circle_menu)).check(matches(isDisplayed()));
        onView(withId(R.id.textView5)).check(matches(isDisplayed()));
    }

    @SmallTest
    public void test(){
        onView(withId(R.id.circle_menu)).perform(click());
        onView(withId(R.id.circle_menu)).perform(click());
    }

}
