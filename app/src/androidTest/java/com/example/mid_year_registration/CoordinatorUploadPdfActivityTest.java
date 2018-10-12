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
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CoordinatorUploadPdfActivityTest extends ActivityInstrumentationTestCase2<CoordinatorUploadPdfActivity> {
    CoordinatorUploadPdfActivity activity;

    public CoordinatorUploadPdfActivityTest() {
        super(CoordinatorUploadPdfActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra("filename", "test.pdf");
        intent.putExtra("studentNumber", "1234567");
        intent.putExtra("courseCode", "COMS2001");
        setActivityIntent(intent);
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.PdfView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.attachmentFab)).check(matches(isDisplayed()));
        onView(withId(R.id.attachmentFab)).check(matches(isClickable()));
        onView(withId(R.id.attachmentFab)).check(matches(isClickable()));
    }

    @Test
    @SmallTest
    public void testBackButton(){

        Intents.init();
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
        onView(withContentDescription("Navigate up")).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CoordinatorUploadActivity.class)));
        Intents.release();
    }

    @Test
    @SmallTest
    public void testLogoutButton(){

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
