//package com.example.mid_year_registration;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.espresso.intent.Intents;
//import android.support.test.filters.SmallTest;
//import android.test.ActivityInstrumentationTestCase2;
//
//import org.junit.Test;
//
//import static android.support.test.InstrumentationRegistry.getTargetContext;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.swipeDown;
//import static android.support.test.espresso.action.ViewActions.swipeUp;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.intent.Intents.intended;
//import static android.support.test.espresso.intent.Intents.intending;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
//import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
//public class StudentConcessionsActivityTest extends ActivityInstrumentationTestCase2<StudentConcessionsActivity> {
//
//    StudentConcessionsActivity activity;
//
//    public StudentConcessionsActivityTest(){
//        super(StudentConcessionsActivity.class);
//    }
//
//    @Override
//    protected void setUp() throws Exception{
//        super.setUp();
//        activity = getActivity();
//    }
//
//    @SmallTest
//    public void testVisibility(){
//        // dismiss the progress dialog
//        if(activity.getmProgressDialog().isShowing()){
//            activity.getmProgressDialog().dismiss();
//        }
//
//        onView(withId(R.id.studentRecyclerView)).check(matches(isDisplayed()));
//
//    }
//
//    @SmallTest
//    public void testListScroll(){
//        // dismiss the progress dialog
//        if(activity.getmProgressDialog().isShowing()){
//            activity.getmProgressDialog().dismiss();
//        }
//
//        onView(withId(R.id.studentRecyclerView)).perform(swipeUp());
//        onView(withId(R.id.studentRecyclerView)).perform(swipeDown());
//    }
//
////    @Test
////    @SmallTest
////    public void testBackButton(){
////        // dismiss the progress dialog
////        if(activity.getmProgressDialog().isShowing()){
////            activity.getmProgressDialog().dismiss();
////        }
////
////        Intents.init();
////        Intent resultData = new Intent();
////        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
////        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
////        onView(withContentDescription("Navigate up")).perform(click());
////        intended(hasComponent(new ComponentName(getTargetContext(), StudentMenuActivity.class)));
////        Intents.release();
////    }
//
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
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
//        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
//        onView(withText(R.string.logout)).perform(click());
//        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
//        Intents.release();
//    }
//
//}
