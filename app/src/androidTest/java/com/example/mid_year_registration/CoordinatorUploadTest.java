package com.example.mid_year_registration;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import com.example.mid_year_registration.CoordinatorMenuActivity;

import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


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

    @Test
    @SmallTest
    public void testBackButton(){
        // dismiss the progress dialog
        if(activity.getmProgressDialog().isShowing()){
            activity.getmProgressDialog().dismiss();
        }

        Intents.init();
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
        onView(withContentDescription("Navigate up")).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CoordinatorMenuActivity.class)));
        Intents.release();
    }

    @Test
    @SmallTest
    public void testLogoutButton(){
        // dismiss the progress dialog
        if(activity.getmProgressDialog().isShowing()){
            activity.getmProgressDialog().dismiss();
        }

        Intents.init();
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.logout)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
        Intents.release();
    }

}
