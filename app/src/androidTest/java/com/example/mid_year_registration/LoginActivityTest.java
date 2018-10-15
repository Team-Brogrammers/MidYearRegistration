package com.example.mid_year_registration;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    LoginActivity activity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).check(matches(isClickable()));
        onView(withId(R.id.resetPasswordTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.creatAccountTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));

    }

    @SmallTest
    public void testEmailValidation(){
        assertEquals(activity.isValidEmail(null), false);
//        assertEquals(activity.isValidEmail("1234@gmail.com"), false);
        assertEquals(activity.isValidEmail("123456@wits.ac.za"), true);
    }

    @SmallTest
    public void testPasswordValidation(){
        assertEquals(activity.isValidPassword(null), false);
        assertEquals(activity.isValidPassword("abc"), false);
        assertEquals(activity.isValidPassword("goodPassword1"), true);
    }

    @SmallTest
    public void testNeedAccountButton(){

        Intents.init();
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
        onView(withId(R.id.creatAccountTextView)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), SignUpActivity.class)));
        Intents.release();
    }

    @SmallTest
    public void testForgotPasswordButton(){
        Intents.init();
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
        onView(withId(R.id.resetPasswordTextView)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), PasswordResetActivity.class)));
        Intents.release();
    }
}
