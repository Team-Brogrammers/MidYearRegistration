package com.example.mid_year_registration;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

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

public class StudentViewConcessionActivityTest extends ActivityInstrumentationTestCase2<StudentViewConcessionActivity> {
    StudentViewConcessionActivity activity;

    public StudentViewConcessionActivityTest(){
        super(StudentViewConcessionActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra("name", "9876543_COMS1099_2018-09-27.pdf");
        intent.putExtra("studentNo","9876543");
        intent.putExtra("course","COMS1099");
        intent.putExtra("comment", "No Comment");
        setActivityIntent(intent);
        activity = getActivity();
    }

//    @SmallTest
//    public void testVisibility(){
//        // dismiss the progress dialog
//        if(activity.getmProgressDialog().isShowing()){
//            activity.getmProgressDialog().dismiss();
//        }
//
//        onView(withId(R.id.tvStudentConcessionCourse)).check(matches(isDisplayed()));
//        onView(withId(R.id.tvStudentConcessionCourseVal)).check(matches(isDisplayed()));
//        onView(withId(R.id.tvCommentView)).check(matches(isDisplayed()));
//        onView(withId(R.id.StudentPdfView)).check(matches(isDisplayed()));
//    }

//    @Test
//    @SmallTest
//    public void testBackButton(){
//        // dismiss the progress dialog
//        if(activity.getmProgressDialog().isShowing()){
//            activity.getmProgressDialog().dismiss();
//        }
//
//        Intents.init();
//        Intent resultData = new Intent();
//        resultData.putExtra("name", "9876543_COMS1099_2018-09-27.pdf");
//        resultData.putExtra("studentNo","9876543");
//        resultData.putExtra("course","COMS1099");
//        resultData.putExtra("comment", "No Comment");
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
//        onView(withContentDescription("Navigate up")).perform(click());
//        intended(hasComponent(new ComponentName(getTargetContext(), StudentConcessionsActivity.class)));
//        Intents.release();
//    }

//    @Test
//    @SmallTest
//    public void testLogoutButton(){
//        // dismiss the progress dialog
//        if(activity.getmProgressDialog().isShowing()){
//            activity.getmProgressDialog().dismiss();
//        }
//
//        Intents.init();
//        Intent resultData = new Intent();
//        resultData.putExtra("name", "9876543_COMS1099_2018-09-27.pdf");
//        resultData.putExtra("studentNo","9876543");
//        resultData.putExtra("course","COMS1099");
//        resultData.putExtra("comment", "No Comment");
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
//        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
//        onView(withText(R.string.logout)).perform(click());
//        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
//        Intents.release();
//    }

}
