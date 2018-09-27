package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import com.example.mid_year_registration.AddCoursesActivity;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class AddCoursesTest extends ActivityInstrumentationTestCase2<AddCoursesActivity> {

    AddCoursesActivity activity;

    public AddCoursesTest() {
        super(AddCoursesActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){

        onView(withId(R.id.course1EditText)).check(matches(isDisplayed()));
        onView(withId(R.id.course2EditText)).check(matches(isDisplayed()));
        onView(withId(R.id.course3EditText)).check(matches(isDisplayed()));
        onView(withId(R.id.addEmailTextInputLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView5)).check(matches(isDisplayed()));

    }
}
